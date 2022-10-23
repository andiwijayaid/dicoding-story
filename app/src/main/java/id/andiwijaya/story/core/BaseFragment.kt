package id.andiwijaya.story.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import id.andiwijaya.story.R
import id.andiwijaya.story.presentation.component.StoryBottomDialog

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    companion object {
        const val TAG_ERROR = "error"
    }

    protected lateinit var binding: T
    lateinit var storyBottomDialog: StoryBottomDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)

        return binding.root
    }

    protected abstract fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    fun <A> observeDataFlow(
        liveData: LiveData<out Result<A>>,
        onLoad: (() -> Unit)? = null,
        onError: ((Result<A>) -> Unit)? = { showErrorDialog() },
        onSuccess: ((A) -> Unit)? = null,
    ) {
        liveData.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result.status) {
                    Status.LOADING -> onLoad?.invoke()
                    Status.ERROR -> onError?.invoke(
                        Result.Error(
                            result.message.orEmpty(), null, result.code
                        )
                    )
                    Status.SUCCESS -> it.data?.let { data -> onSuccess?.invoke(data) }
                }
            }
        }
    }

    fun showErrorDialog(
        message: String? = null,
        buttonText: String? = null
    ) {
        storyBottomDialog = StoryBottomDialog(
            context?.getString(R.string.general_error_title),
            message ?: context?.getString(R.string.general_error_message),
            buttonText ?: context?.getString(R.string.close)
        )
        storyBottomDialog.onClickListener = object : StoryBottomDialog.OnButtonClickListener {
            override fun onPrimaryClickedListener() {
                storyBottomDialog.dismiss()
            }

            override fun onSecondaryClickedListener() = Unit
        }
        storyBottomDialog.show(childFragmentManager, TAG_ERROR)
    }

    fun showBottomDialog(
        title: String,
        message: String,
        primaryButtonText: String,
        icon: Int? = null,
        buttonAction: () -> Unit
    ) {
        storyBottomDialog = StoryBottomDialog(title, message, primaryButtonText, icon = icon)
        storyBottomDialog.onClickListener = object : StoryBottomDialog.OnButtonClickListener {
            override fun onPrimaryClickedListener() {
                buttonAction.invoke()
            }

            override fun onSecondaryClickedListener() = Unit
        }
        storyBottomDialog.show(childFragmentManager, TAG_ERROR)
    }

    fun showConfirmationDialog(
        title: String? = null,
        message: String? = null,
        primaryButtonText: String? = null,
        secondaryButtonText: String? = null,
        primaryAction: (() -> Unit)? = null
    ) {
        storyBottomDialog = StoryBottomDialog(
            title ?: context?.getString(R.string.general_error_title),
            message ?: context?.getString(R.string.general_error_message),
            primaryButtonText ?: context?.getString(R.string.agree),
            secondaryButtonText ?: context?.getString(R.string.dismiss),
            true
        )
        storyBottomDialog.onClickListener = object : StoryBottomDialog.OnButtonClickListener {
            override fun onPrimaryClickedListener() {
                primaryAction?.invoke() ?: storyBottomDialog.dismiss()
            }

            override fun onSecondaryClickedListener() {
                storyBottomDialog.dismiss()
            }
        }
        storyBottomDialog.show(childFragmentManager, TAG_ERROR)
    }

}