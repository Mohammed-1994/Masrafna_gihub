package com.example.masrafna.ui.services.loans_and_financing.viewPager

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masrafna.R
import com.example.masrafna.data.models.LoanDetailsByBank
import com.example.masrafna.databinding.FragmentAboutLoanBinding


class AboutLoanFragment : Fragment() {

    private lateinit var binding :FragmentAboutLoanBinding
    private lateinit var loanDetails: LoanDetailsByBank.Payload

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loanDetails = requireArguments().getParcelable("details")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            aboutLoanText.text = Html.fromHtml(loanDetails.about, Html.FROM_HTML_MODE_COMPACT)
            loanBoundValue.text = loanDetails.limits
            loanBenefitValue.text = loanDetails.benefit
            loanPeriodValue.text = loanDetails.period
            warrantiesText.text = Html.fromHtml(loanDetails.warranties, Html.FROM_HTML_MODE_COMPACT)
        }
    }
}