package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.andiwijaya.story.R
import id.andiwijaya.story.core.BaseFragment
import id.andiwijaya.story.databinding.FragmentHomeBinding
import id.andiwijaya.story.presentation.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
                            findNavController().navigate(HomeFragmentDirections.actionHomeToLogin())
                        }
                    }
                    else -> {}
                }
                return true
            }
        })
    }

}