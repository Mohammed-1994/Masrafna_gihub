package com.example.masrafna.ui.services.loans_and_financing

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.R
import com.example.masrafna.data.models.LoanListResponse
import com.example.masrafna.data.models.Localizations
import com.example.masrafna.databinding.LoansItemBinding

private const val TAG = "LocalizationListAdapter myTag"

class LoansListAdapter(val context: Context) :
    RecyclerView.Adapter<LoansListAdapter.LoansViewHolder>() {

    lateinit var loans: MutableList<LoanListResponse.Payload.Data?>
    var fromBankPage = false

    inner class LoansViewHolder(val binding: LoansItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoansViewHolder {
        val view = LoansItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoansViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoansViewHolder, position: Int) {
        with(holder.binding) {
            val currentLoan = loans[position]!!
            loansBtn.text = currentLoan.name


            loansBtn.setOnClickListener {

                val bundle = bundleOf(
                    "loan_name" to currentLoan.name,
                    "loan_id" to currentLoan.id,
                    "from_bank_page" to fromBankPage
                )


                if (fromBankPage)
                    it.findNavController().navigate(
                        R.id.action_loan_list_fragment_to_viewPagerDetailsFragment, bundle
                    )
                else
                    it.findNavController().navigate(
                        R.id.action_from_personal_loans_list_to_loan_details_fragment, bundle
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return loans.size
    }


}