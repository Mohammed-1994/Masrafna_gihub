package com.example.masrafna.ui.services.loans_and_financing

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
import com.example.masrafna.data.models.AvailableBankList
import com.example.masrafna.data.models.BankListModel
import com.example.masrafna.databinding.BankItemBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.schedulers.Schedulers

private const val TAG = "BankListAdapter myTag"

class AvailableBanksAdapter(val context: Context) :
    RecyclerView.Adapter<AvailableBanksAdapter.BankViewHolder>() {

    var loanId = ""
    lateinit var banks: MutableList<AvailableBankList.Payload.AvailableForBank?>


    inner class BankViewHolder(val binding: BankItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val view = BankItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BankViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
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
                    "bank_id" to currentBank.id,
                    "loan_id" to loanId
                )
                it.findNavController()
                    .navigate(R.id.action_cards_fragment_to_cardDetailsFragment, bundle)
            }


        }
    }


    override fun getItemCount(): Int {
        return banks.size
    }


}