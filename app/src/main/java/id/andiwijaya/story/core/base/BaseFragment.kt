package id.andiwijaya.story.core.base

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.andiwijaya.story.BuildConfig
import id.andiwijaya.story.R
import id.andiwijaya.story.core.PermissionsListener
import id.andiwijaya.story.core.Result
import id.andiwijaya.story.core.Status
import id.andiwijaya.story.core.util.orFalse
import id.andiwijaya.story.core.util.orTrue
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

    protected fun pop(navDirections: NavDirections) = findNavController().navigate(
        navDirections,
        NavOptions.Builder().apply {
            setEnterAnim(R.anim.slide_in_left)
            setExitAnim(R.anim.slide_out_right)
            setPopEnterAnim(R.anim.slide_in_right)
            setPopExitAnim(R.anim.slide_out_left)
            setPopUpTo(R.id.nav_graph, true)
            setLaunchSingleTop(true)
        }.build()
    )

    protected fun goTo(navDirections: NavDirections) = findNavController().navigate(
        navDirections,
        NavOptions.Builder().apply {
            setEnterAnim(R.anim.slide_in_right)
            setExitAnim(R.anim.slide_out_left)
            setPopEnterAnim(R.anim.slide_in_left)
            setPopExitAnim(R.anim.slide_out_right)
        }.build()
    )

    protected fun navigateWithExtras(
        navDirections: NavDirections, extras: FragmentNavigator.Extras
    ) = findNavController().navigate(navDirections, extras)

    protected fun back() = findNavController().navigateUp()

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
                storyBottomDialog.dismiss()
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
        secondaryAction: (() -> Unit)? = null,
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
                primaryAction?.invoke()
                storyBottomDialog.dismiss()
            }

            override fun onSecondaryClickedListener() {
                secondaryAction?.invoke()
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

    protected fun askPermissions(listener: PermissionsListener) {
        val permissions = if (SDK_INT >= Build.VERSION_CODES.R) {
            listOf(Manifest.permission.CAMERA)
        } else listOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        Dexter.withContext(context)
            .withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.areAllPermissionsGranted().orFalse()) {
                        listener.onPermissionsGranted()
                    } else listener.onPermissionsDenied()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            })
            .withErrorListener { listener.onPermissionsDenied() }
            .check()
    }

    protected fun showPermissionDeniedDialog() = showConfirmationDialog(
        getString(R.string.ask_permission_title),
        getString(R.string.ask_permission_description),
        getString(R.string.ask_permission_primary_button)
    ) {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${BuildConfig.APPLICATION_ID}")
            )
        )
    }

    protected fun askLocationPermission(listener: PermissionsListener) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.grantedPermissionResponses?.isEmpty().orTrue()) {
                        listener.onPermissionsDenied()
                    } else listener.onPermissionsGranted()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            })
            .withErrorListener { listener.onPermissionsDenied() }
            .check()
    }
}