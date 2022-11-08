package id.andiwijaya.story.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.BuildConfig
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.ONE
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.core.PermissionsListener
import id.andiwijaya.story.core.base.BaseFragment
import id.andiwijaya.story.databinding.FragmentHomeBinding
import id.andiwijaya.story.presentation.adapter.StoryAdapter
import id.andiwijaya.story.presentation.adapter.StoryLoadStateAdapter
import id.andiwijaya.story.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private val adapter by lazy {
        StoryAdapter { story, imageView ->
            navigateWithExtras(
                HomeFragmentDirections.actionHomeToDetail(story),
                FragmentNavigatorExtras(imageView to story.id)
            )
        }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        viewModel.getStories(ONE)
        rvStory.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        rvStory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rvStory.adapter = adapter.withLoadStateFooter(
            footer = StoryLoadStateAdapter { viewModel.getStories(ONE) }
        )
        rvStory.layoutManager = LinearLayoutManager(context)
        viewModel.stories.observe(viewLifecycleOwner) {
            lifecycleScope.launch { adapter.submitData(it) }
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                rvStory.smoothScrollToPosition(ZERO)
            }
        })
        srlStory.setOnRefreshListener { adapter.refresh() }
        adapter.addLoadStateListener {
            srlStory.isRefreshing = it.refresh is LoadState.Loading
            tvHomeReload.isVisible = it.refresh is LoadState.Error
        }
        tvHomeReload.setOnClickListener { adapter.refresh() }
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.logout -> {
                        showConfirmationDialog(
                            getString(R.string.logout),
                            getString(R.string.logout_confirmation),
                            getString(R.string.logout)
                        ) {
                            viewModel.logOut()
                            pop(HomeFragmentDirections.actionHomeToLogin())
                        }
                    }
                    else -> {
                        askPermissions(object : PermissionsListener {
                            override fun onPermissionsGranted() {
                                goTo(HomeFragmentDirections.actionHomeToAddNewStory())
                            }

                            override fun onPermissionsDenied() {
                                showConfirmationDialog(
                                    getString(R.string.ask_permission_title),
                                    getString(R.string.ask_permission_description),
                                    getString(R.string.ask_permission_primary_button)
                                ) {
                                    startActivity(
                                        Intent(
                                            ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                                        )
                                    )
                                }
                            }
                        })
                    }
                }
                return true
            }
        })
    }

}