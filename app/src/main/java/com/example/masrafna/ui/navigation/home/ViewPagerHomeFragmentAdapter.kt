package com.example.masrafna.ui.navigation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.data.models.CardHomeFragmentModel
import com.example.masrafna.databinding.MainViewPagerItemBinding

class ViewPagerHomeFragmentAdapter(val context: Context) :
    RecyclerView.Adapter<ViewPagerHomeFragmentAdapter.ViewHolder>() {

    var list = ArrayList<CardHomeFragmentModel>()

    open inner class ViewHolder(val binding: MainViewPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val view =
            MainViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.image.setImageResource(list[position].image)
            binding.root.setOnClickListener {
                Toast.makeText(context, list[position].title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun submitList(articlesList: ArrayList<CardHomeFragmentModel>) {
        this.list = articlesList
        notifyDataSetChanged()
    }
}