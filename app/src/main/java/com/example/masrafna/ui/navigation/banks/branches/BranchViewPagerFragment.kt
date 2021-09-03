package com.example.masrafna.ui.navigation.banks.branches

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankBranche
import com.example.masrafna.databinding.FragmentBranchViewPagerBinding
import com.example.masrafna.ui.navigation.banks.BankServicesAdapter.Companion.ATM
import com.example.masrafna.ui.navigation.banks.BankServicesAdapter.Companion.BRANCHES
import com.example.masrafna.ui.navigation.banks.BankServicesAdapter.Companion.POS
import com.example.masrafna.ui.navigation.banks.BanksViewModel
import com.example.masrafna.util.Cities

private const val TAG = "BranchViewPagerFragment myTag"

class BranchViewPagerFragment : Fragment() {

    private var cityId = 0
    private var serviceType = 0
    private var bankId = ""
    private var bankName = ""
    private val banksViewModel: BanksViewModel by viewModels()
    private lateinit var binding: FragmentBranchViewPagerBinding
    private lateinit var adapter: BankBranchesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityId = it.getInt("city")
            serviceType = it.getInt("service_type")
            bankId = it.getString("id").toString()
            bankName = it.getString("bank_name").toString()
        }

        with(banksViewModel) {
            networkStatus.observe(this@BranchViewPagerFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE

            })

            banksBranchesResponse.observe(this@BranchViewPagerFragment, {
                populateBranches(it)
            })

            banksPOSsResponse.observe(this@BranchViewPagerFragment, {
                populatePOSs(it)
            })

            banksAtMsResponse.observe(this@BranchViewPagerFragment, {
                populateATMs(it)
            })
        }
    }

    private fun populateATMs(ATMs: BankBranche?) {
        if (ATMs != null) {
            adapter.branches = ATMs.payload.data.toMutableList()
            adapter.notifyDataSetChanged()
            binding.newsRv.adapter = adapter
        }
    }

    private fun populatePOSs(POSs: BankBranche?) {
        if (POSs != null) {
            adapter.branches = POSs.payload.data.toMutableList()
            adapter.notifyDataSetChanged()
            binding.newsRv.adapter = adapter
        }


    }

    private fun populateBranches(branches: BankBranche?) {
        if (branches != null) {
            adapter.branches = branches.payload.data.toMutableList()
            adapter.notifyDataSetChanged()
            binding.newsRv.adapter = adapter
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBranchViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)

        adapter = BankBranchesAdapter(requireContext()).apply {
            bankName = this@BranchViewPagerFragment.bankName
        }
        val cityName = Cities.getCities()[cityId].name
        var serviceTypeString = ""
        when (serviceType) {
            BRANCHES -> {
                getBranches()
                serviceTypeString = "فروع المصرف"
            }
            POS -> {
                getPos()
                serviceTypeString = "نقاط البيع"
            }
            ATM -> {
                getATMs()
                serviceTypeString = "الصرافات الالية"
            }
        }


        binding.cityName.text = "$serviceTypeString  في  $cityName"


    }

    private fun getATMs() {
        banksViewModel.getBanksATMs(bankId, cityId)
    }

    private fun getPos() {
        banksViewModel.getBanksPOSs(bankId, cityId)
    }

    private fun getBranches() {

        banksViewModel.getBanksBranches(bankId, cityId)

    }


}
