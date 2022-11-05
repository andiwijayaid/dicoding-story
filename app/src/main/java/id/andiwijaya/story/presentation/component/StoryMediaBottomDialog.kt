package id.andiwijaya.story.presentation.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.andiwijaya.story.databinding.ComponentStoryMediaBottomDialogBinding
import id.andiwijaya.story.presentation.util.invisible
import id.andiwijaya.story.presentation.util.show

class StoryMediaBottomDialog : BottomSheetDialogFragment() {

    lateinit var onClickListener: OnClickListener
    lateinit var binding: ComponentStoryMediaBottomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComponentStoryMediaBottomDialogBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAction()
    }

    private fun setAction() = with(binding) {
        tvGallery.setOnClickListener {
            if (::onClickListener.isInitialized) {
                onClickListener.onGalleryClickedListener()
                tvGallery.invisible()
                tvCamera.invisible()
                pbLoadImage.show()
            }
        }
        tvCamera.setOnClickListener {
            if (::onClickListener.isInitialized) {
                tvGallery.invisible()
                tvCamera.invisible()
                pbLoadImage.show()
                onClickListener.onCameraClickedListener()
            }
        }
    }

    interface OnClickListener {
        fun onGalleryClickedListener()
        fun onCameraClickedListener()
    }

}