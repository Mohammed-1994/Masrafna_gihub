package com.example.masrafna.ui.services.cards

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.allViews
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.data.models.CardsModel
import com.example.masrafna.databinding.FragmentCardTypeBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity


private const val TAG = "CardTypeFragment myTag"

class CardTypeFragment : Fragment() {


    private lateinit var binding: FragmentCardTypeBinding
    private lateinit var cardsModel: CardsModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cards = ArrayList<CardsModel>()
        cards.add(CardsModel(0, getString(R.string.debit_cards), "", ""))
        cards.add(CardsModel(0, getString(R.string.prepaid_cards), "", ""))
        cards.add(CardsModel(0, getString(R.string.credit_card), "", ""))

        setupToolbar()
        val cardClicked = View.OnClickListener {
            findNavController().navigate(R.id.action_cards_fragment_to_cardDetailsFragment)
        }


        for (card in binding.cardsList.allViews) {
            card.setOnClickListener(cardClicked)
        }

    }

    private fun setupToolbar() {

        with(binding) {
            toolbar.drawerIcon.setOnClickListener {
                (requireContext() as NavigationDrawerActivity)
                    .binding.drawerLayout.open()
            }
            toolbar.navigateUp.setOnClickListener {
                findNavController().navigateUp()
            }
            toolbar.title.visibility = GONE

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }


}