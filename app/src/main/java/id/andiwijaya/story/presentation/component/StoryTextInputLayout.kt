package id.andiwijaya.story.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.databinding.ComponentStoryTextInputLayoutBinding
import id.andiwijaya.story.presentation.util.hide
import id.andiwijaya.story.presentation.util.show

class StoryTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0
) : ConstraintLayout(context, attrs, defStyleArr) {

    private var minChar = ZERO

    private val binding: ComponentStoryTextInputLayoutBinding =
        ComponentStoryTextInputLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.StoryTextInputLayout, defStyleArr, ZERO)
            .run {
                setText(getString(R.styleable.StoryTextInputLayout_text).orEmpty())
                setHint(getString(R.styleable.StoryTextInputLayout_hint).orEmpty())
                setIcon(getResourceId(R.styleable.StoryTextInputLayout_iconSrc, ZERO))
                setInputType(getInt(R.styleable.StoryTextInputLayout_android_inputType, ZERO))
                setEditTextFocusChangeListener()
                setEditTextChangedListener()
                setEditTextMinChar(getInt(R.styleable.StoryTextInputLayout_minChar, ZERO))
                recycle()
            }
    }

    private fun setEditTextMinChar(minChar: Int) {
        this.minChar = minChar
    }

    fun getEditText() = binding.textInputEditText

    fun setError(message: String?) {
        binding.textInputLayout.error = message
    }

    private fun setEditTextFocusChangeListener() = with(binding) {
        textInputEditText.setOnFocusChangeListener { _, hasFocus ->
            ivTilIcon.setColorFilter(if (hasFocus) R.color.main_color else R.color.grey)
        }
    }

    private fun setEditTextChangedListener() = with(binding) {
        textInputEditText.addTextChangedListener {
            textInputLayout.isErrorEnabled = false
            binding.textInputEditText.error = if (text.length < minChar && text.length != ZERO) {
                context.getString(R.string.min_n_char, minChar)
            } else null
        }
    }

    private fun setInputType(inputType: Int) {
        binding.textInputEditText.inputType = inputType
    }

    private fun setText(text: String) = binding.textInputEditText.setText(text)

    private fun setHint(hint: String) {
        binding.textInputLayout.hint = hint
    }

    private fun setIcon(icon: Int) = with(binding) {
        if (icon != ZERO) {
            ivTilIcon.show()
            ivTilIcon.setImageResource(icon)
        } else ivTilIcon.hide()
    }

    val text: String
        get() = binding.textInputEditText.text.toString()

}