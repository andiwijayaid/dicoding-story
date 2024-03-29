package id.andiwijaya.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.andiwijaya.story.core.util.DateTimeUtil.timeDifference
import id.andiwijaya.story.databinding.ItemStoryBinding
import id.andiwijaya.story.domain.model.Story
import id.andiwijaya.story.presentation.util.StoryDiffCallback

class StoryAdapter(
    private val onItemSelected: (Story, ImageView) -> Unit
) : PagingDataAdapter<Story, StoryAdapter.ViewHolder>(StoryDiffCallback) {

    class ViewHolder(
        private val binding: ItemStoryBinding,
        val onItemSelected: (Story, ImageView) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) = with(binding) {
            tvItemName.text = story.name
            tvItemTime.text = timeDifference(story.createdAt, root.context)
            tvItemDescription.text = story.description
            ViewCompat.setTransitionName(ivItemPhoto, story.id)
            Glide.with(root.context).load(story.photoUrl).into(ivItemPhoto)
            container.setOnClickListener { onItemSelected(story, ivItemPhoto) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemSelected
    )

}