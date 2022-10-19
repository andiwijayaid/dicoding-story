package id.andiwijaya.story.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.EMPTY_STRING
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.databinding.ComponentStoryButtonBinding
import id.andiwijaya.story.presentation.hide
import id.andiwijaya.story.presentation.show

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
                setText(getString(R.styleable.StoryButton_android_text).orEmpty())
                buttonText = binding.button.text.toString()
                recycle()
            }
    }

    private fun setText(text: String) {
        binding.button.text = text
    }

    fun isLoading(state: Boolean) = with(binding) {
        if (state) {
            button.text = EMPTY_STRING
            progressBar.show()
        } else {
            button.text = buttonText
            progressBar.hide()
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding.button.setOnClickListener(l)
    }

}