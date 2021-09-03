package com.example.masrafna.ui.navigation.news

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masrafna.R
import com.example.masrafna.data.models.NewsListModel
import com.example.masrafna.databinding.NewsItemBinding
import org.ocpsoft.prettytime.PrettyTime

private const val TAG = "NewsAdapter myTag"

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    lateinit var newsListList: MutableList<NewsListModel.Payload.Data?>
    inner class NewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        Log.d(TAG, "onCreateViewHolder: ")
        val view = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        with(holder.binding) {
            val news = newsListList[position]!!
            newsTitleItem.text = news.title
            newsDescItem.text= news.content
            newsDateItem.text = PrettyTime().format(news.createdAt)
            Glide.with(context)
                .load(news.image)
                .placeholder(R.drawable.sticker)
                .into(newsImageItem)

            moreBtn.setOnClickListener {
                val id = bundleOf("id" to news.id)
                it.findNavController().navigate(R.id.action_fragment_news_list_to_fragment_news, id)

            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${newsListList.size}")
        return newsListList.size
    }




}