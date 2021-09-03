package com.example.masrafna.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masrafna.R
import com.example.masrafna.databinding.FragmentFourthBinding
import com.example.masrafna.ui.loging.LoggingActivity

class FourthFragment : Fragment() {
    private lateinit var binding: FragmentFourthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            onBoardingFinished()
            startActivity(Intent(requireContext(), LoggingActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.first_time_preference),
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.first_time_finished), true)
            apply()
        }
    }
}