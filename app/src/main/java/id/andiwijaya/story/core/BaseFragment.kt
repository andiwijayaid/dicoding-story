package id.andiwijaya.story.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import id.andiwijaya.story.R
import id.andiwijaya.story.presentation.component.StoryBottomDialog

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    companion object {
        const val TAG_ERROR = "error"
    }

    private lateinit var onBackPressedCallback: OnBackPressedCallback
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

    protected fun observeNavigation(viewModel: BaseViewModel) = with(viewModel) {
        navigation.observe(viewLifecycleOwner) {
            when (it) {
                is NavigationCommand.Pop -> {
                    findNavController().navigate(
                        it.directions,
                        NavOptions.Builder().apply {
                            setPopUpTo(R.id.nav_graph, true)
                            setLaunchSingleTop(true)
                        }.build()
                    )
                }
                is NavigationCommand.GoTo -> findNavController().navigate(it.directions)
            }
        }
    }

    fun showErrorDialog(
        message: String? = null,
        buttonText: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        storyBottomDialog = StoryBottomDialog(
            context?.getString(R.string.general_error_title),
            message ?: context?.getString(R.string.general_error_message),
            buttonText ?: context?.getString(R.string.close)
        )
        storyBottomDialog.onClickListener = object : StoryBottomDialog.OnButtonClickListener {
            override fun onPrimaryClickedListener() {
                onButtonClick?.invoke()
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

    override fun onStart() {
        super.onStart()
        if (::onBackPressedCallback.isInitialized) activity?.onBackPressedDispatcher?.addCallback(
            onBackPressedCallback
        )
    }

    override fun onStop() {
        if (::onBackPressedCallback.isInitialized) onBackPressedCallback.remove()
        super.onStop()
    }

    fun setupBackPressCallback(onBackPressed: () -> Unit) {
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed.invoke()
            }
        }
    }

}