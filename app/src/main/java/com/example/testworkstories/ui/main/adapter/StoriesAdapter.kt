package com.example.testworkstories.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testworkstories.data.model.Story
import com.example.testworkstories.databinding.RcItemStoriesBinding
import com.example.testworkstories.ui.main.ItemClickListener

class StoriesAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<StoriesAdapter.MyHolder>() {

    private var listItem: MutableList<Story> = mutableListOf()

    class MyHolder(private val binding: RcItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            setImage(story.imageLogo)
            setName(story.newsName)
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

        val holder = MyHolder(view)

        view.root.setOnClickListener {
            itemClickListener.onItemClickCategory(holder.bindingAdapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListStories(list: List<Story>) {
        if (list.isEmpty() || list != listItem) {
            listItem.addAll(list)
            notifyDataSetChanged()
        }
    }
}