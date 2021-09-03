package com.example.masrafna.ui.services.loans_and_financing.viewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.LoanDetailsByBank
import com.example.masrafna.databinding.FragmentBankViewPagerDetailsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.services.loans_and_financing.LoansViewModel
import com.google.android.material.tabs.TabLayoutMediator

class BankViewPagerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBankViewPagerDetailsBinding

    private val loansViewModel: LoansViewModel by viewModels()
    private lateinit var viewPager: ViewPager2

    private var loanId = ""
    private var bankId = ""
    private var fromBankPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loanId = requireArguments().getString("loan_id").toString()
        bankId = requireArguments().getString("bank_id").toString()
        fromBankPage = requireArguments().getBoolean("from_bank_page")


        with(loansViewModel) {
            networkStatus.observe(this@BankViewPagerDetailsFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            loanDetailsByBankResponse.observe(this@BankViewPagerDetailsFragment, {
                populateResult(it)
            })
            loanDetails.observe(this@BankViewPagerDetailsFragment, {
                populateResult(it)
            })

        }
    }

    private fun populateResult(response: LoanDetailsByBank?) {
        if (response != null) {
            val viewPagerAdapter = DetailsViewPagerAdapter(this)
            viewPagerAdapter.loanDetail = response.payload

            viewPager = binding.pager
            viewPager.adapter = viewPagerAdapter

            val tabLayout = binding.tabLayout
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "حول القرض"
                    }
                    1 -> {
                        tab.text = "آلية القرض"
                    }
                    2 -> {
                        tab.text = "الشروط"
                    }
                    3 -> {
                        tab.text = "آلية التسديد"
                    }
                }
            }.attach()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBankViewPagerDetailsBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        if (fromBankPage)
            loansViewModel.getLoanDetails(loanId)
        else
            loansViewModel.getLoanDetailsByBank(loanId, bankId)
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
            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    class DetailsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


        lateinit var loanDetail: LoanDetailsByBank.Payload

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    val fragment = AboutLoanFragment()
                    fragment.arguments = Bundle().apply {
                        putParcelable("details", loanDetail)
                    }
                    return fragment
                }
                else -> {
                    val fragment = LoanDetailsFragment()
                    fragment.arguments = Bundle().apply {
                        putParcelable("details", loanDetail)
                        putInt("position", position)
                    }
                    return fragment
                }
            }

        }
    }

}