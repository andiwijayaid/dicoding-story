package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.core.Constants.ONE
import id.andiwijaya.story.databinding.FragmentHomeBinding
import id.andiwijaya.story.presentation.adapter.StoryAdapter
import id.andiwijaya.story.presentation.adapter.StoryLoadStateAdapter
import id.andiwijaya.story.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    private val adapter by lazy { StoryAdapter() }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeNavigation(viewModel)
        viewModel.getStories(ONE)
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = StoryLoadStateAdapter {
                viewModel.getStories(ONE)
            }
        )
        binding.rvStory.layoutManager = LinearLayoutManager(context)
        viewModel.stories.observe(viewLifecycleOwner) {
            lifecycleScope.launch { adapter.submitData(it) }
        }
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
                        }
                    }
                    else -> {}
                }
                return true
            }
        })
    }

}