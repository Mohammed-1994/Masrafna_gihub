package com.example.masrafna.ui.services.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.data.models.ServicesModel
import com.example.masrafna.databinding.ServicesGridItemBinding

class ServicesListAdapter(val context: Context, private val serviceClicked: OnServiceClicked) :
    RecyclerView.Adapter<ServicesListAdapter.ServicesViewHolder>() {

    private var services = ArrayList<ServicesModel>()


    inner class ServicesViewHolder(val binding: ServicesGridItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val view = ServicesGridItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ServicesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        val currentService = services[position]
        with(holder) {
            binding.image.setImageResource(currentService.image)
            binding.desc.text = currentService.desc
            binding.title.text = currentService.title

            binding.root.setOnClickListener {
                serviceClicked.onClick(currentService)
            }
        }
    }

    override fun getItemCount(): Int {
        return services.size
    }

    fun submitArticles(services: ArrayList<ServicesModel>) {
        this.services = services
        notifyDataSetChanged()
    }

    interface OnServiceClicked{
        fun  onClick(service: ServicesModel)
    }


}