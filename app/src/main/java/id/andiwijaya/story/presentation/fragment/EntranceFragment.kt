package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.databinding.FragmentEntranceBinding
import id.andiwijaya.story.presentation.viewmodel.EntranceViewModel

@AndroidEntryPoint
class EntranceFragment : BaseFragment<FragmentEntranceBinding>() {

    private val viewModel by viewModels<EntranceViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentEntranceBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val action: NavDirections = when {
            viewModel.isTokenBlank() -> {
                EntranceFragmentDirections.actionToLogin()
            }
            else -> {
                EntranceFragmentDirections.actionToHome()
            }
        }
        this.findNavController().navigate(action)
    }

}