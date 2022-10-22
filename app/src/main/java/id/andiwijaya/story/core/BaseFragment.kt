package id.andiwijaya.story.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import id.andiwijaya.story.presentation.component.ErrorDialog

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    companion object {
        const val TAG_ERROR = "error"
    }

    protected lateinit var binding: T
    lateinit var errorDialog: ErrorDialog

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
        title: String? = null,
        buttonText: String? = null
    ) {
        errorDialog = ErrorDialog(title, message, buttonText)
        errorDialog.onClickListener = object : ErrorDialog.OnButtonClickListener {
            override fun onButtonClickedListener() {
                errorDialog.dismiss()
            }
        }
        errorDialog.show(childFragmentManager, TAG_ERROR)
    }

}