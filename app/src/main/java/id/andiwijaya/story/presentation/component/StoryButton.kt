package id.andiwijaya.story.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.databinding.ComponentStoryButtonBinding
import id.andiwijaya.story.presentation.util.hide
import id.andiwijaya.story.presentation.util.show

class StoryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = ZERO
) : ConstraintLayout(context, attrs, defStyleArr) {

    private var buttonText = EMPTY_STRING
    private val binding: ComponentStoryButtonBinding =
        ComponentStoryButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.StoryButton, defStyleArr, ZERO)
            .run {
                text = getString(R.styleable.StoryButton_android_text).orEmpty()
                buttonText = binding.button.text.toString()
                recycle()
            }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.button.setOnClickListener {
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(windowToken, ZERO)
            l?.onClick(this)
        }
    }

    var text: String
        get() = binding.button.text.toString()
        set(value) {
            binding.button.text = value
        }

    fun isLoading(state: Boolean) = with(binding) {
        button.isEnabled = !state
        if (state) {
            button.text = EMPTY_STRING
            progressBar.show()
        } else {
            button.text = buttonText
            progressBar.hide()
        }
    }

    fun isEnabled(enable: Boolean) {
        binding.button.isEnabled = enable
    }

}