package com.example.masrafna.ui.services.online

import android.content.Context
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
import com.example.masrafna.data.models.OnlineServiceModel
import com.example.masrafna.databinding.FragmentOnlineServiceBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.services.localization.LocalizationListAdapter


private const val TAG = "OnlineServiceFragment myTag"

class OnlineServiceFragment : Fragment() {

    private var mContext: Context? = null

    private lateinit var binding: FragmentOnlineServiceBinding
    private val onlineViewModel: OnlineViewModel by viewModels()

    private lateinit var onlineBanksListAdapter: OnlineBanksListAdapter
    private lateinit var layoutManager: GridLayoutManager


    private var page = 1
    private var lastPage = 0
    private var isLoading = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(onlineViewModel) {
            networkStatus.observe(this@OnlineServiceFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            onlineServiceBanks.observe(this@OnlineServiceFragment, {
                populateResult(it)
            })
        }
    }

    private fun populateResult(response: OnlineServiceModel?) {

        if (response != null) {

            lastPage = response.payload.meta.lastPage
            isLoading = false
            if (::onlineBanksListAdapter.isInitialized) {

                if (page == 1) {
                    Log.d(TAG, "populateResult: page = 1, ${response.payload.data.size}")
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.banksRv.layoutManager = layoutManager

                    onlineBanksListAdapter.banks.clear()
                    binding.banksRv.adapter = onlineBanksListAdapter

                }
                onlineBanksListAdapter.banks.addAll(response.payload.data)

            } else {
                Log.d(TAG, "populateResult: adapter is not init")
                onlineBanksListAdapter = OnlineBanksListAdapter(requireContext())
                layoutManager = GridLayoutManager(requireContext(), 2)

                onlineBanksListAdapter.banks = response.payload.data.toMutableList()
                binding.banksRv.layoutManager = layoutManager
                binding.banksRv.adapter = onlineBanksListAdapter

            }

            onlineBanksListAdapter.notifyDataSetChanged()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnlineServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = 1
        setupToolbar()
        mContext = requireContext()
        getBanks()
        setupScrolling()

    }

    private fun setupScrolling() {
        binding.banksRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx < 0) {

                    val manager = binding.banksRv.layoutManager!! as LinearLayoutManager

                    val visibleItemCount = manager.childCount
                    val pastVisibleItem = manager.findFirstCompletelyVisibleItemPosition()

                    val total = onlineBanksListAdapter.itemCount

                    if (!isLoading) {
                        if (visibleItemCount + pastVisibleItem >= total) {

                            page++
                            if (page <= lastPage)
                                getBanks()

                        }

                    }
                }
            }
        })
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
            toolbar.title.visibility = View.GONE

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    private fun getBanks() {
        isLoading = true
        onlineViewModel.getOnlineServicesBanks(page)

    }

}