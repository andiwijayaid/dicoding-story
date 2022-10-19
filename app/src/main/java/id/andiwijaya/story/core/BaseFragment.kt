package id.andiwijaya.story.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T

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
        onError: ((Result<A>) -> Unit)? = null,
        onSuccess: ((A) -> Unit)? = null,
    ) {
        liveData.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result.status) {
                    Status.LOADING -> onLoad?.invoke()
                    Status.ERROR -> onError?.invoke(
                        Result.Error("")
                    )
                    Status.SUCCESS -> it.data?.let { data ->
                        onSuccess?.invoke(data)
                    }
                }
            }
        }
    }

}