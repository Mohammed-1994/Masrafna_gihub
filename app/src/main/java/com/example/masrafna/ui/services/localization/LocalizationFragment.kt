package com.example.masrafna.ui.services.localization

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.Localizations
import com.example.masrafna.databinding.FragmentLocalizationBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity


private const val TAG = "LocalizationFragment myTag"

class LocalizationFragment : Fragment() {

    private lateinit var binding: FragmentLocalizationBinding
    private lateinit var localizationListAdapter: LocalizationListAdapter
    private val localViewModel: LocalViewModel by viewModels()

    private var page = 1
    private var lastPage = 0
    private var isLoading = false
    private lateinit var layoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(localViewModel) {
            networkStatus.observe(this@LocalizationFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else GONE
            })

            localizationsResponse.observe(this@LocalizationFragment, {
                populateResult(it)
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocalizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = 1
        setupToolbar()
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

                    val total = localizationListAdapter.itemCount

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

    private fun populateResult(response: Localizations?) {


        if (response != null) {

            lastPage = response.payload.meta.lastPage
            isLoading = false
            if (::localizationListAdapter.isInitialized) {

                if (page == 1) {
                    Log.d(TAG, "populateResult: page = 1, ${response.payload.data.size}")
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.banksRv.layoutManager = layoutManager

                    localizationListAdapter.banks.clear()
                    binding.banksRv.adapter = localizationListAdapter

                }
                localizationListAdapter.banks.addAll(response.payload.data)

            } else {
                Log.d(TAG, "populateResult: adapter is not init")
                localizationListAdapter = LocalizationListAdapter(requireContext())
                layoutManager = GridLayoutManager(requireContext(), 2)

                localizationListAdapter.banks = response.payload.data.toMutableList()
                binding.banksRv.layoutManager = layoutManager
                binding.banksRv.adapter = localizationListAdapter

            }

            localizationListAdapter.notifyDataSetChanged()
        }


    }

    fun getBanks() {
        isLoading = true
        localViewModel.getLocalizations(page)
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