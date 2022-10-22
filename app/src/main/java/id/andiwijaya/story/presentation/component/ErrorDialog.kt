package id.andiwijaya.story.presentation.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.andiwijaya.story.databinding.ComponentErrorDialogBinding

class ErrorDialog(
    private val title: String? = null,
    private val message: String? = null,
    private val buttonText: String? = null
) : BottomSheetDialogFragment() {

    lateinit var onClickListener: OnButtonClickListener
    lateinit var binding: ComponentErrorDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComponentErrorDialogBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() = with(binding) {
        tvErrorTitle.text = title?.takeIf { it.isNotBlank() } ?: tvErrorTitle.text
        tvErrorMessage.text = message?.takeIf { it.isNotBlank() } ?: tvErrorMessage.text
        btPrimary.text = buttonText?.takeIf { it.isNotBlank() } ?: btPrimary.text
        btPrimary.setOnClickListener {
            if (::onClickListener.isInitialized) {
                onClickListener.onButtonClickedListener()
            }
        }
    }

    interface OnButtonClickListener {
        fun onButtonClickedListener()
    }

}