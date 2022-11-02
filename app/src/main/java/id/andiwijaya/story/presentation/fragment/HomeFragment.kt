package id.andiwijaya.story.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.BuildConfig
import id.andiwijaya.story.R
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.core.Constants.ONE
import id.andiwijaya.story.core.PermissionsListener
import id.andiwijaya.story.databinding.FragmentHomeBinding
import id.andiwijaya.story.presentation.adapter.StoryAdapter
import id.andiwijaya.story.presentation.adapter.StoryLoadStateAdapter
import id.andiwijaya.story.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private val adapter by lazy {
        StoryAdapter { goTo(HomeFragmentDirections.actionHomeToDetail(it)) }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        viewModel.getStories(ONE)
        rvStory.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
        rvStory.adapter = adapter.withLoadStateFooter(
            footer = StoryLoadStateAdapter {
                viewModel.getStories(ONE)
            }
        )
        rvStory.layoutManager = LinearLayoutManager(context)
        viewModel.stories.observe(viewLifecycleOwner) {
            lifecycleScope.launch { adapter.submitData(it) }
        }
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