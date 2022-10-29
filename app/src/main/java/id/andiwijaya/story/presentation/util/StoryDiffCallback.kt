package id.andiwijaya.story.presentation.util

import androidx.recyclerview.widget.DiffUtil
import id.andiwijaya.story.domain.model.Story

object StoryDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }
}