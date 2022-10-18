package id.andiwijaya.story.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.andiwijaya.story.databinding.FragmentEntranceBinding

class EntranceFragment : Fragment() {

    private lateinit var binding: FragmentEntranceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntranceBinding.inflate(inflater, container, false)
        return binding.root.also {
            val action = EntranceFragmentDirections.actionToLogin()
            this.findNavController().navigate(action)
        }
    }

}