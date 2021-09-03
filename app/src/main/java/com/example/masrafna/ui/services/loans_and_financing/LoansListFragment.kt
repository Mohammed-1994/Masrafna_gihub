package com.example.masrafna.ui.services.loans_and_financing

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.LoanListResponse
import com.example.masrafna.databinding.FragmentLoansListBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.navigation.banks.branches.BankBranchesAdapter
import com.example.masrafna.ui.navigation.news.NewsAdapter
import com.example.masrafna.ui.services.loans_and_financing.LoansFinancingFragment.Companion.BANKS_LOANS
import com.example.masrafna.ui.services.loans_and_financing.LoansFinancingFragment.Companion.ISLAMIC_FINANCING
import com.example.masrafna.ui.services.loans_and_financing.LoansTypeFragment.Companion.COMPANIES_LOAN
import com.example.masrafna.ui.services.loans_and_financing.LoansTypeFragment.Companion.INDIVIDUAL_LOAN


private const val TAG = "LoansListFragment myTag"

class LoansListFragment : Fragment() {

    private lateinit var binding: FragmentLoansListBinding
    private var viewType = 1
    private var personalOrCompany = 1

    private val loansViewModel: LoansViewModel by viewModels()
    private lateinit var adapter: LoansListAdapter

    private var bankId = ""
    private var fromBankPage = false
    private var page = 1
    private var isLoading = false
    private var lastPage = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewType = requireArguments().getInt("view_type")
        personalOrCompany = requireArguments().getInt("personal_company")
        bankId = requireArguments().getString("bank_id").toString()
        fromBankPage = requireArguments().getBoolean("from_bank_page")


        with(loansViewModel) {

            networkStatus.observe(this@LoansListFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            loansListResponse.observe(this@LoansListFragment, {
                populateResponse(it)
            })
            bankLoansByTypeResponse.observe(this@LoansListFragment, {
                populateResponse(it)
            })
            fundingByTypeResponse.observe(this@LoansListFragment, {
                populateResponse(it)
            })

        }
    }

    private fun populateResponse(response: LoanListResponse?) {

        if (response != null) {
            Log.d(TAG, "populateResult: ${response.payload.data.size}")
            lastPage = response.payload.meta.lastPage
            isLoading = false
            if (::adapter.isInitialized) {

                if (page == 1) {
                    adapter.loans.clear()
                    binding.loansRv.adapter = adapter
                }
                adapter.loans.addAll(response.payload.data)

            } else {
                Log.d(TAG, "populateResult: adapter is not init")
                adapter = LoansListAdapter(requireContext())
                adapter.fromBankPage = fromBankPage
                adapter.loans = response.payload.data.toMutableList()
                binding.loansRv.adapter = adapter
            }
            adapter.notifyDataSetChanged()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoansListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        page = 1
        setupToolbar()
        getLoans()
        setupPaging()

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

    private fun getLoans() {

        when (personalOrCompany) {
            INDIVIDUAL_LOAN -> {
                when (viewType) {
                    BANKS_LOANS -> {

                        /**
                         * get individual loans
                         * loans type = 0, bank type = 1
                         */

                        if (fromBankPage)
                            loansViewModel.getBankLoansByType(
                                loanType = INDIVIDUAL_LOAN,
                                bankId = bankId,
                                page
                            )
                        else
                            loansViewModel.getLoansByTypeAndBankType(
                                loanType = INDIVIDUAL_LOAN,
                                bankType = 1,
                                page = page
                            )
                        with(binding) {
                            toolbar.title.text = "قروض أفراد"
                        }
                    }
                    ISLAMIC_FINANCING -> {

                        /**
                         * get individual funding.
                         * loans type = 0, bank type = 0
                         */
                        if (fromBankPage)
                            loansViewModel.getFundingByTypeBankPage(
                                loanType = INDIVIDUAL_LOAN,
                                bankId = bankId,
                                page
                            )
                        else
                            loansViewModel.getFundingByType(
                                loanType = INDIVIDUAL_LOAN,
                                page
                            )
                        with(binding) {
                            toolbar.title.text = "تمويلات أفراد"
                        }
                    }
                }
            }

            COMPANIES_LOAN -> {
                when (viewType) {
                    BANKS_LOANS -> {

                        /**
                         * get companies loans
                         * loans type = 1, bank type = 1
                         */
                        if (fromBankPage)
                            loansViewModel.getBankLoansByType(
                                loanType = COMPANIES_LOAN,
                                bankId = bankId,
                                page
                            )
                        else
                            loansViewModel.getLoansByTypeAndBankType(
                                loanType = COMPANIES_LOAN,
                                bankType = 1,
                                page = page
                            )
                        with(binding) {
                            toolbar.title.text = "قروض شركات"
                        }
                    }
                    ISLAMIC_FINANCING -> {


                        /**
                         * get individual funding
                         * loans type = 1, bank type = 0
                         */
                        if (fromBankPage)
                            loansViewModel.getFundingByTypeBankPage(
                                loanType = COMPANIES_LOAN,
                                bankId = bankId,
                                page
                            )
                        else
                            loansViewModel.getFundingByType(
                                loanType = COMPANIES_LOAN,
                                page
                            )
                        with(binding) {
                            toolbar.title.text = "تمويلات شركات"
                        }
                    }
                }
            }
        }
    }

    private fun setupPaging() {

        binding.loansRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    val manager = binding!!.loansRv.layoutManager!! as LinearLayoutManager

                    val visibleItemCount = manager.childCount
                    val pastVisibleItem = manager.findFirstCompletelyVisibleItemPosition()

                    val total = adapter.itemCount

                    if (!isLoading) {
                        if (visibleItemCount + pastVisibleItem >= total) {

                            page++
                            Log.d(TAG, "onScrolled: $lastPage, $pastVisibleItem")
                            if (page <= lastPage) {
                                getLoans()
                            }

                        }

                    }
                }
            }
        })
    }

}