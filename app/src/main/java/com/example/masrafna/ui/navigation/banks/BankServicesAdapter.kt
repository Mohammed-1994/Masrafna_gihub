package com.example.masrafna.ui.navigation.banks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.R
import com.example.masrafna.data.models.BankListModel
import com.example.masrafna.data.models.BankServiceModel
import com.example.masrafna.databinding.BankServiceItemBinding
import com.example.masrafna.ui.navigation.banks.BankDetailsFragment.Companion.BANKS_LOANS
import com.example.masrafna.ui.navigation.banks.BankDetailsFragment.Companion.ISLAMIC_FINANCING
import com.example.masrafna.ui.services.loans_and_financing.LoansFinancingFragment

class BankServicesAdapter(val context: Context) :
    RecyclerView.Adapter<BankServicesAdapter.BankServiceViewHolder>() {


    var banksList = ArrayList<BankServiceModel>()
    lateinit var mBank: BankListModel.Payload.Data

    inner class BankServiceViewHolder(val binding: BankServiceItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankServiceViewHolder {
        val view = BankServiceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BankServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BankServiceViewHolder, position: Int) {

        with(holder.binding) {
            title.text = banksList[position].title
            image.setImageResource(banksList[position].icon)
            layout.setOnClickListener {
                var serviceName = 0
                when (position) {
                    3 -> {
                        serviceName = ATM
                        val bundle = bundleOf(
                            "id" to mBank.id,
                            "type" to mBank.type,
                            "service_type" to serviceName,
                            "bank_name" to mBank.name

                        )
                        it.findNavController().navigate(
                            R.id.action_nav_bankDetailsFragment_to_branchesFragment,
                            bundle
                        )
                    }
                    4 -> {
                        serviceName = POS
                        val bundle = bundleOf(
                            "id" to mBank.id,
                            "type" to mBank.type,
                            "service_type" to serviceName,
                            "bank_name" to mBank.name
                        )
                        it.findNavController().navigate(
                            R.id.action_nav_bankDetailsFragment_to_branchesFragment,
                            bundle
                        )
                    }
                    5 -> {
                        serviceName = BRANCHES
                        val bundle = bundleOf(
                            "id" to mBank.id,
                            "type" to mBank.type,
                            "service_type" to serviceName,
                            "bank_name" to mBank.name
                        )
                        it.findNavController().navigate(
                            R.id.action_nav_bankDetailsFragment_to_branchesFragment,
                            bundle
                        )
                    }
                    0 -> {
                        var viewType = 0
                        if (mBank.type == 0)
                            viewType = ISLAMIC_FINANCING
                        else if (mBank.type == 1)
                            viewType = BANKS_LOANS
                        val bundle = bundleOf(
                            "view_type" to viewType,
                            "from_bank_page" to true, "bank_id" to mBank.id
                        )
                        it.findNavController().navigate(
                            R.id.action_nav_bankDetailsFragment_to_loanListFragment,
                            bundle
                        )
                    }
                }
            }
        }

    }

    fun setBank(bank: BankListModel.Payload.Data) {
        mBank = bank
    }

    override fun getItemCount(): Int {
        return banksList.size
    }

    companion object {
        const val BRANCHES = 100
        const val ATM = 200
        const val POS = 300
    }

}