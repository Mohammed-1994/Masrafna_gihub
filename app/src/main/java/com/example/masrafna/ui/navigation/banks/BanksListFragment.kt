package com.example.masrafna.ui.navigation.banks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankListModel
import com.example.masrafna.databinding.FragmentBanksListBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity


private const val TAG = "BanksListFragment myTag"

class BanksListFragment : Fragment() {

    private lateinit var binding: FragmentBanksListBinding
    private lateinit var bankListAdapter: BankListAdapter
    private val banksViewModel: BanksViewModel by viewModels()


    private var page = 1
    private var lastPage = 0
    private var isLoading = false
    private var search = false
    private var query = ""
    private lateinit var layoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(banksViewModel) {
            networkStatus.observe(this@BanksListFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            banksListResponse.observe(this@BanksListFragment, {
                populateResult(it)
            })

        }

    }

    private fun populateResult(response: BankListModel?) {
        if (response != null) {

            lastPage = response.payload.meta.lastPage
            isLoading = false
            if (::bankListAdapter.isInitialized) {

                if (page == 1) {
                    Log.d(TAG, "populateResult: page = 1, ${response.payload.data.size}")
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.banksRv.layoutManager = layoutManager

                    bankListAdapter.banks.clear()
                    binding.banksRv.adapter = bankListAdapter

                }
                bankListAdapter.banks.addAll(response.payload.data)

            } else {
                Log.d(TAG, "populateResult: adapter is not init")
                bankListAdapter = BankListAdapter(requireContext())
                layoutManager = GridLayoutManager(requireContext(), 2)

                bankListAdapter.banks = response.payload.data.toMutableList()
                binding.banksRv.layoutManager = layoutManager
                binding.banksRv.adapter = bankListAdapter

            }

            bankListAdapter.notifyDataSetChanged()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBanksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = 1

        setupToolbar()
        getBanks()
        setupScrolling()

        binding.searchButton.setOnClickListener {
            Log.d(TAG, "onViewCreated: on click")
            if (binding.searchEt.text.toString().isNotEmpty())
                searchByName(binding.searchEt.text.toString().trim())
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

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    private fun setupScrolling() {
        binding.banksRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx < 0) {

                    val manager = binding.banksRv.layoutManager!! as LinearLayoutManager

                    val visibleItemCount = manager.childCount
                    val pastVisibleItem = manager.findFirstCompletelyVisibleItemPosition()

                    val total = bankListAdapter.itemCount

                    if (!isLoading) {
                        if (visibleItemCount + pastVisibleItem >= total) {

                            page++
                            if (page <= lastPage)
                                if (!search)
                                    getBanks()
                                else
                                    searchByName(query)

                        }

                    }
                }
            }
        })
    }

    private fun searchByName(query: String) {
        Log.d(TAG, "searchByName: $query")
        search = true
        isLoading = true
        banksViewModel.searchBanksByName(query)
    }

    private fun getBanks() {
        search = false
        isLoading = true
        banksViewModel.getBanksList()
    }


}