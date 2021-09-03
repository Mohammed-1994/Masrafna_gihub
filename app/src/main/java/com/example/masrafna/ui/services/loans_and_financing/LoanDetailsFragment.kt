package com.example.masrafna.ui.services.loans_and_financing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.AvailableBankList
import com.example.masrafna.databinding.FragmentLoanDetailsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity

class LoanDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLoanDetailsBinding
    private lateinit var adapter: AvailableBanksAdapter
    private lateinit var layoutManager: GridLayoutManager

    private val loansViewModel: LoansViewModel by viewModels()

    private var loanName = ""
    private var loanId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loanName = requireArguments().getString("loan_name").toString()
        loanId = requireArguments().getString("loan_id").toString()

        with(loansViewModel) {

            networkStatus.observe(this@LoanDetailsFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            availableBankResponse.observe(this@LoanDetailsFragment, {

                populateResponse(it)
            })

        }
    }

    private fun populateResponse(response: AvailableBankList?) {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = AvailableBanksAdapter(requireContext())
        adapter.loanId = this.loanId
        if (response != null) {
            binding.loanText.text = response.payload.aboutLoan
            adapter.banks = response.payload.availableForBanks.toMutableList()
            adapter.notifyDataSetChanged()
            binding.availableBanksRv.layoutManager = layoutManager
            binding.availableBanksRv.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoanDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loanTitle.text = loanName
        loansViewModel.getAvailableBankList(loanId)
        binding.availableBanks.text = "المصارف التي تقدم $loanName"
        setupToolbar()
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