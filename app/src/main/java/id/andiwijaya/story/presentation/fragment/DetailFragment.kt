package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.core.base.BaseFragment
import id.andiwijaya.story.core.util.DateTimeUtil.convertDateAndTime
import id.andiwijaya.story.databinding.FragmentDetailBinding
import id.andiwijaya.story.presentation.viewmodel.DetailViewModel

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel by viewModels<DetailViewModel>()
    private val args: DetailFragmentArgs by navArgs()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processArgs(args)
        ViewCompat.setTransitionName(ivDetailStory, viewModel.story?.id.orEmpty())
        tvDetailName.text = viewModel.story?.name.orEmpty()
        tvDetailDescription.text = viewModel.story?.description.orEmpty()
        tvDetailTime.text = convertDateAndTime(viewModel.story?.createdAt.orEmpty())
        context?.let { context ->
            Glide.with(context).load(viewModel.story?.photoUrl).into(ivDetailStory)
        }
        stbDetail.setNavigationOnClickListener { back() }
    }

}