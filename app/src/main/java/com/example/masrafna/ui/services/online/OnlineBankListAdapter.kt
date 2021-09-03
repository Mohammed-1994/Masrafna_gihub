package com.example.masrafna.ui.services.online

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.masrafna.R
import com.example.masrafna.api.MainAPIClient
import com.example.masrafna.data.auth.request.AddToFavoriteModel
import com.example.masrafna.data.models.Localizations
import com.example.masrafna.data.models.OnlineServiceModel
import com.example.masrafna.databinding.BankItemBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.schedulers.Schedulers

private const val TAG = "LocalizationListAdapter myTag"

class OnlineBanksListAdapter(val context: Context) :
    RecyclerView.Adapter<OnlineBanksListAdapter.OnlineServiceBanksViewHolder>() {

    lateinit var banks: MutableList<OnlineServiceModel.Payload.Data?>


    inner class OnlineServiceBanksViewHolder(val binding: BankItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnlineServiceBanksViewHolder {
        val view = BankItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OnlineServiceBanksViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnlineServiceBanksViewHolder, position: Int) {
        with(holder.binding) {
            val currentBank = banks[position]!!
            title.text = currentBank.name

            Glide.with(context)
                .load(currentBank.logo)
                .placeholder(R.drawable.bank_image)
                .into(image)

            if (currentBank.isFavorite)
                favoriteIcon.setImageResource(R.drawable.favorite_true)
            else
                favoriteIcon.setImageResource(R.drawable.favorite_false)


            image.setOnClickListener {
                val bundle = bundleOf(
                    "id" to currentBank.id,
                    "name" to currentBank.name
                )
                it.findNavController()
                    .navigate(
                        R.id.action_nav_onlineServiceFragment_to_onlineServicesDetailsFragment,
                        bundle
                    )
            }


        }
    }

    override fun getItemCount(): Int {
        return banks.size
    }


}