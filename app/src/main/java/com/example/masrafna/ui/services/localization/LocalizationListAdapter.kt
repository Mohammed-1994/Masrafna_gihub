package com.example.masrafna.ui.services.localization

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
import com.example.masrafna.data.models.BankListModel
import com.example.masrafna.data.models.Localizations
import com.example.masrafna.databinding.BankItemBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.schedulers.Schedulers

private const val TAG = "LocalizationListAdapter myTag"

class LocalizationListAdapter(val context: Context) :
    RecyclerView.Adapter<LocalizationListAdapter.LocalizationViewHolder>() {

    lateinit var banks: MutableList<Localizations.Payload.Data?>


    inner class LocalizationViewHolder(val binding: BankItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalizationViewHolder {
        val view = BankItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LocalizationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocalizationViewHolder, position: Int) {
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
                    .navigate(R.id.action_to_localization_bank_fragment, bundle)
            }
            favoriteIcon.setOnClickListener {
                if (currentBank.isFavorite)
                    removeFromFavorite(currentBank, this)
                else
                    addToFavorite(currentBank, this)
            }


        }
    }

    private fun addToFavorite(bank: Localizations.Payload.Data, bankItemBinding: BankItemBinding) {
        MainAPIClient.getClient()
            .addToFavorite(AddToFavoriteModel(bank.id))
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    (context as NavigationDrawerActivity)
                        .runOnUiThread {
                            bankItemBinding.favoriteIcon.setImageResource(
                                R.drawable.favorite_true
                            )
                            Snackbar.make(
                                bankItemBinding.root,
                                "تمت الاضافة للمفضلة",
                                Snackbar.LENGTH_SHORT
                            ).show()

                            bank.isFavorite = true
                        }

                },
                {
                    Log.e(TAG, "addToFavorite: Error", it)
                })
    }

    private fun removeFromFavorite(bank: Localizations.Payload.Data, bankItemBinding: BankItemBinding) {
        MainAPIClient.getClient()
            .removeFromFavorite(bank.id)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    (context as NavigationDrawerActivity)
                        .runOnUiThread {
                            bankItemBinding.favoriteIcon.setImageResource(
                                R.drawable.favorite_false
                            )
                            Snackbar.make(
                                bankItemBinding.root,
                                "تمت الازالة من المفضلة",
                                Snackbar.LENGTH_SHORT
                            ).show()
                            bank.isFavorite = false
                        }

                },
                {
                    Log.e(TAG, "addToFavorite: Error", it)
                })
    }
    override fun getItemCount(): Int {
        return banks.size
    }


}