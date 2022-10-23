package id.andiwijaya.story.presentation.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.andiwijaya.story.core.util.orFalse
import id.andiwijaya.story.databinding.ComponentStoryBottomDialogBinding

class StoryBottomDialog(
    private val title: String? = null,
    private val message: String? = null,
    private val primaryButtonText: String? = null,
    private val secondaryButtonText: String? = null,
    private val showSecondaryButton: Boolean? = false,
    private val icon: Int? = null
) : BottomSheetDialogFragment() {

    lateinit var onClickListener: OnButtonClickListener
    lateinit var binding: ComponentStoryBottomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComponentStoryBottomDialogBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() = with(binding) {
        icon?.let { ivIcon.setImageResource(it) }
        tvErrorTitle.text = title?.takeIf { it.isNotBlank() } ?: tvErrorTitle.text
        tvErrorMessage.text = message?.takeIf { it.isNotBlank() } ?: tvErrorMessage.text
        btPrimary.text = primaryButtonText?.takeIf { it.isNotBlank() } ?: btPrimary.text
        btSecondary.text = secondaryButtonText?.takeIf { it.isNotBlank() } ?: btPrimary.text
        btPrimary.setOnClickListener {
            if (::onClickListener.isInitialized) onClickListener.onPrimaryClickedListener()
        }
        btSecondary.isVisible = showSecondaryButton.orFalse()
        btSecondary.setOnClickListener {
            if (::onClickListener.isInitialized) onClickListener.onSecondaryClickedListener()
        }
    }

    interface OnButtonClickListener {
        fun onPrimaryClickedListener()
        fun onSecondaryClickedListener()
    }

}