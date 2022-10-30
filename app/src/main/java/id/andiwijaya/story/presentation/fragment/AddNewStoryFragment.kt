package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.databinding.FragmentAddNewStoryBinding
import id.andiwijaya.story.presentation.viewmodel.AddNewStoryViewModel

@AndroidEntryPoint
class AddNewStoryFragment : BaseFragment<FragmentAddNewStoryBinding>() {

    private val viewModel by viewModels<AddNewStoryViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAddNewStoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}