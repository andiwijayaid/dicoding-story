package id.andiwijaya.story.presentation.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.BuildConfig
import id.andiwijaya.story.core.base.BaseFragment
import id.andiwijaya.story.core.util.FileUtil.createCustomTempFile
import id.andiwijaya.story.core.util.FileUtil.reduceFileImage
import id.andiwijaya.story.core.util.FileUtil.toMultiBodyPart
import id.andiwijaya.story.core.util.FileUtil.uriToFile
import id.andiwijaya.story.databinding.FragmentAddNewStoryBinding
import id.andiwijaya.story.presentation.component.StoryMediaBottomDialog
import id.andiwijaya.story.presentation.viewmodel.AddNewStoryViewModel
import java.io.File

@AndroidEntryPoint
class AddNewStoryFragment : BaseFragment<FragmentAddNewStoryBinding>() {

    private val viewModel by viewModels<AddNewStoryViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAddNewStoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        stbAddNewStory.setNavigationOnClickListener { back() }
        ivAddStory.setOnClickListener { mediaDialog.show(childFragmentManager, TAG_ERROR) }
        btAddStory.setOnClickListener { viewModel.postStory() }
        edAddDescription.addTextChangedListener {
            viewModel.description = it.toString()
            validateForm()
        }
        observeUploadResult()
        viewModel.isButtonEnable.observe(viewLifecycleOwner) {
            btAddStory.isEnabled(it)
        }
    }

    private fun observeUploadResult() = with(binding) {
        observeDataFlow(viewModel.genericResult, onLoad = {
            btAddStory.isLoading(true)
        }, onError = {
            btAddStory.isLoading(false)
            showErrorDialog(it.message)
        }) {
            findNavController().popBackStack()
            btAddStory.isLoading(false)
            back()
        }
    }

    private fun validateForm() = with(viewModel) { isButtonEnable.postValue(isAllFilled()) }

    private fun startGallery() {
        Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
            launcherIntentGallery.launch(Intent.createChooser(this, "Choose a Picture"))
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = context?.let { uriToFile(selectedImg, it).reduceFileImage() }
            viewModel.photo = myFile?.toMultiBodyPart(myFile.name)
            binding.ivAddStory.setImageURI(selectedImg)
            validateForm()
        }
        mediaDialog.dismiss()
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        context?.let { context ->
            createCustomTempFile(context).also {
                val photoUri: Uri = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID,
                    it
                )
                viewModel.photoFromCameraPath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                launcherIntentCamera.launch(intent)
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(viewModel.photoFromCameraPath).reduceFileImage()
            viewModel.photo = myFile.toMultiBodyPart(myFile.name)
            binding.ivAddStory.setImageBitmap(BitmapFactory.decodeFile(myFile.path))
            validateForm()
        }
        mediaDialog.dismiss()
    }

    private val mediaDialog = StoryMediaBottomDialog().apply {
        onClickListener = object : StoryMediaBottomDialog.OnClickListener {
            override fun onGalleryClickedListener() = startGallery()
            override fun onCameraClickedListener() = startCamera()
        }
    }

}