package id.andiwijaya.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.andiwijaya.story.databinding.ItemStoryBinding
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.presentation.util.StoryDiffCallback

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.ViewHolder>(StoryDiffCallback) {

    class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) = with(binding) {
            tvName.text = story.name
            tvTime.text = story.createdAt
            tvDescription.text = story.description
            Glide.with(root.context).load(story.photoUrl).into(ivStory)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

}