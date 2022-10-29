package id.andiwijaya.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.andiwijaya.story.databinding.ItemStoryLoadFooterBinding

class StoryLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<StoryLoadStateAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemStoryLoadFooterBinding,
        retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ibReload.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            pbStory.isVisible = loadState is LoadState.Loading || loadState !is LoadState.NotLoading
            ibReload.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        ItemStoryLoadFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )

}