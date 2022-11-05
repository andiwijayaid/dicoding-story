package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.core.Constants.Argument.ARG_KEY_ID
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import id.andiwijaya.story.core.util.DateTimeUtil.convertDateAndTime
import id.andiwijaya.story.databinding.FragmentDetailBinding
import id.andiwijaya.story.presentation.util.hide
import id.andiwijaya.story.presentation.util.show
import id.andiwijaya.story.presentation.viewmodel.DetailViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel by viewModels<DetailViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        stbDetail.setNavigationOnClickListener { back() }
        viewModel.getStory(arguments?.getString(ARG_KEY_ID, EMPTY_STRING).orEmpty())
        observeDataFlow(viewModel.story, onLoad = {
            clgStoryDetailContent.hide()
            pbStoryDetail.show()
        }, onError = {
            clgStoryDetailContent.hide()
            pbStoryDetail.hide()
            showErrorDialog { back() }
        }) {
            clgStoryDetailContent.show()
            pbStoryDetail.hide()
            tvName.text = it.name
            tvDescription.text = it.description
            tvTime.text = convertDateAndTime(it.createdAt)
            context?.let { context -> Glide.with(context).load(it.photoUrl).into(ivStory) }
        }
    }

}