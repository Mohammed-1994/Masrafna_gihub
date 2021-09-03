package com.example.masrafna.ui.services.loans_and_financing.viewPager

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masrafna.R
import com.example.masrafna.data.models.LoanDetailsByBank
import com.example.masrafna.databinding.FragmentLoanDetails2Binding
import com.example.masrafna.databinding.FragmentLoanDetailsBinding

private const val TAG = "LoanDetailsFragment myTag"

class LoanDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLoanDetails2Binding
    private lateinit var loanDetails: LoanDetailsByBank.Payload
    private var positions = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(TAG, "onCreateView: ")
        binding = FragmentLoanDetails2Binding.inflate(inflater, container, false)
        loanDetails = requireArguments().getParcelable("details")!!
        positions = requireArguments().getInt("position")

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        with(binding) {
            when (positions) {
                1 -> {
                    detailsTitle.text = "آلية القرض"
                    detailsText.text =
                        Html.fromHtml(loanDetails.loanMechanism, Html.FROM_HTML_MODE_COMPACT)
                }
                2 -> {
                    detailsTitle.text = "الشروط"
                    detailsText.text =
                        Html.fromHtml(loanDetails.conditions, Html.FROM_HTML_MODE_COMPACT)
                }
                3 -> {
                    detailsTitle.text = "آلية التسديد"
                    detailsText.text =
                        Html.fromHtml(loanDetails.paymentMechanism, Html.FROM_HTML_MODE_COMPACT)
                }
            }
        }

    }

}