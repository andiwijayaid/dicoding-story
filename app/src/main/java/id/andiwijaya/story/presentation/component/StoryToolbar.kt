package id.andiwijaya.story.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import id.andiwijaya.story.R
import id.andiwijaya.story.core.Constants.ZERO
import id.andiwijaya.story.databinding.ComponentStoryToolbarBinding

class StoryToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = ZERO
) : Toolbar(context, attrs, defStyleArr) {

    private val binding: ComponentStoryToolbarBinding =
        ComponentStoryToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.StoryToolbar, defStyleArr, ZERO).run {
            setToolbarTitle(getString(R.styleable.StoryToolbar_title).orEmpty())
            recycle()
        }
    }

    override fun setNavigationOnClickListener(listener: OnClickListener?) {
        binding.ivBack.setOnClickListener { listener?.onClick(it) }
    }

    private fun setToolbarTitle(title: String) {
        binding.tvToolbarTitle.text = title
    }

}