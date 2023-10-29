package com.riskiilyas.newsappdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riskiilyas.newsappdemo.databinding.NewsItemBinding
import com.riskiilyas.newsappdemo.model.everything.Article
import com.riskiilyas.newsappdemo.model.everything.EverythingResponse


class NewsAdapter(
    private val list: MutableList<Article> = mutableListOf(),
    val onItemClicked: (Article) -> Unit
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                Glide.with(root.context).load(article.urlToImage).into(imageView2)
                tvTitle.text = article.title
                tvDesc.text = article.description
                root.setOnClickListener {
                    onItemClicked(article)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(newList: List<Article>) {
        list.clear()
        newList.forEach {
            list.add(it)
        }
        notifyDataSetChanged()
    }
}