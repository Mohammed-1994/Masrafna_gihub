package com.example.masrafna.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masrafna.R
import com.example.masrafna.databinding.FragmentViewPagerBinding
import com.example.masrafna.ui.loging.LoggingActivity

private const val TAG = "ViewPagerFragment myTag"

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        if (!isFirstTime()) {
            Log.d(TAG, "onCreateView: first time")

            val fragmentList = arrayListOf(
                FirstFragment(),
                SecondFragment(),
                ThirdFragment(),
                FourthFragment()
            )

            val adapter = ViewPagerAdapter(
                fragmentList,
                requireActivity().supportFragmentManager,
                lifecycle
            )

            binding.viewPager.adapter = adapter
        } else {
            Log.d(TAG, "onCreateView: not first time")
            startActivity(Intent(requireContext(), LoggingActivity::class.java))
            requireActivity().finish()
        }
        return binding.root
    }

    private fun isFirstTime(): Boolean {

        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.first_time_preference),
            Context.MODE_PRIVATE
        )
        return sharedPref.getBoolean(
            getString(R.string.first_time_finished),
            false
        )

    }

}


