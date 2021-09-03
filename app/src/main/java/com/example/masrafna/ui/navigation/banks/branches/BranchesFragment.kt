package com.example.masrafna.ui.navigation.banks.branches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.masrafna.R
import com.example.masrafna.data.models.City
import com.example.masrafna.databinding.FragmentBrancesBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.navigation.banks.BankServicesAdapter.Companion.ATM
import com.example.masrafna.ui.navigation.banks.BankServicesAdapter.Companion.BRANCHES
import com.example.masrafna.ui.navigation.banks.BankServicesAdapter.Companion.POS
import com.example.masrafna.util.Cities
import com.google.android.material.tabs.TabLayoutMediator

class BranchesFragment : Fragment() {

    private lateinit var binding: FragmentBrancesBinding

    private lateinit var demoCollectionAdapter: CityViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    var serviceType = 0
    var bankId = ""
    var bankName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceType = requireArguments().getInt("service_type")
        bankId = requireArguments().getString("id").toString()
        bankName = requireArguments().getString("bank_name").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrancesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()


        demoCollectionAdapter = CityViewPagerAdapter(this)
        demoCollectionAdapter.serviceType = serviceType
        demoCollectionAdapter.bankId = bankId
        demoCollectionAdapter.bankName = bankName


        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = Cities.getCities()[position].name
        }.attach()

    }


    private fun setupToolbar() {

        with(binding) {
            when (serviceType) {
                BRANCHES -> {
                    toolbar.image.setImageResource(R.drawable.branshes_dark_icon)
                    toolbar.title.text = "فروع المصرف"
                }
                POS -> {
                    toolbar.image.setImageResource(R.drawable.sell_poinst_dark_icon)
                    toolbar.title.text = "نقاط البيع"
                }
                ATM -> {
                    toolbar.image.setImageResource(R.drawable.atm_dark_icon)
                    toolbar.title.text = "الصرافات الآلية"
                }

            }


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


    class CityViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        var serviceType = 0
        var bankId = ""
        var bankName = ""
        override fun getItemCount(): Int = 19

        override fun createFragment(city: Int): Fragment {
            // Return a NEW fragment instance in createFragment(int)
            val fragment = BranchViewPagerFragment()
            fragment.arguments = Bundle().apply {
                // Our object is just an integer :-P
                putInt("city", city)
                putInt("service_type", serviceType)
                putString("id", bankId)
                putString("bank_name", bankName)
            }
            return fragment
        }
    }
}