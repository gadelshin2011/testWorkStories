package com.example.testworkstories.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testworkstories.data.model.Story
import com.example.testworkstories.databinding.RcItemStoriesBinding
import com.example.testworkstories.ui.main.ItemClickListener
import com.example.testworkstories.ui.main.ItemFavoriteClickListener

class StoriesAdapter(private val itemClickListener: ItemClickListener, private val itemFavoriteClickListener: ItemFavoriteClickListener) :
    RecyclerView.Adapter<StoriesAdapter.MyHolder>() {

    private var listItem: MutableList<Story> = mutableListOf()

    class MyHolder(
        private val binding: RcItemStoriesBinding,
        private val itemFavoriteClickListener: ItemFavoriteClickListener
    ) :RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            setImage(story.imageLogo)
            setName(story.newsName)
            setImageFav(story.favorite)

            binding.imageStateFavoriteButton.setOnClickListener {
                itemFavoriteClickListener.onFavoriteClick(story)
                binding.imageStateFavoriteButton.isSelected = story.favorite
            }
        }
        private fun setImageFav(favorite: Boolean) {
            binding.imageStateFavoriteButton.isSelected = favorite
        }

        private fun setImage(imageLogo: String) {
            Glide.with(itemView.context).load(imageLogo).into(binding.imageStories)
        }

        private fun setName(newsName: String) {
            binding.nameStories.text = newsName
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = RcItemStoriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        val holder = MyHolder(view, itemFavoriteClickListener)
        view.root.setOnClickListener {
            val position = holder.bindingAdapterPosition
            val data = listItem[position]
            itemClickListener.onItemClick(position, data)
        }
        return holder
    }
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(listItem[position])
    }
    override fun getItemCount(): Int {
        return listItem.size
    }

    fun setStories(list: List<Story>) {
           val diffCallback = StoryDiffUtil(
                newList = listItem,
                oldList = list
            )
            val diffList = DiffUtil.calculateDiff(diffCallback)
            listItem.clear()
            listItem.addAll(list)
            diffList.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListStories(list: List<Story>,text: String) {
        listItem.clear()
        if (text.isEmpty()) {
            listItem.addAll(list)
        } else {
            val filterList = list.filter { story ->
                story.newsName.contains(text, ignoreCase = true)
            }
            listItem.addAll(filterList)
        }
        notifyDataSetChanged()

    }

    class StoryDiffUtil(
        val newList: List<Story>,
        val oldList: List<Story>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList[newItemPosition].uniqueName == oldList[oldItemPosition].uniqueName
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList[newItemPosition] == oldList[oldItemPosition]
        }

    }

}
