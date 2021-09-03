package com.example.masrafna.ui.navigation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masrafna.R
import com.example.masrafna.data.models.ArticleListModel
import com.example.masrafna.databinding.MainFragmentArticleItemBinding

class ArticleHomeFragmentAdapter(val context: Context) :
    RecyclerView.Adapter<ArticleHomeFragmentAdapter.ArticleViewHolder>() {

    lateinit var articlesList: MutableList<ArticleListModel.Payload.Data?>


    inner class ArticleViewHolder(val binding: MainFragmentArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = MainFragmentArticleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder.binding) {
            val currentArticle = articlesList[position]!!

            articleTitle.text = currentArticle.title
            articleDesc.text = currentArticle.content

            Glide.with(context)
                .load(currentArticle.image)
                .placeholder(R.drawable.sticker)
                .into(articleImage)

            moreBtn.setOnClickListener {
                val id = bundleOf("id" to currentArticle.id)
                it.findNavController()
                    .navigate(R.id.action_fragment_home_to_fragment_notification, id)

            }
        }
    }

    override fun getItemCount(): Int {
        return articlesList.size
    }


}