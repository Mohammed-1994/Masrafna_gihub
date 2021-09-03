package com.example.masrafna.ui.loging.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.databinding.FragmentWelcomeBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.util.Session

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = Session.verifyOTPResponse.payload.user

        findNavController().backStack.remove()

        binding.welcomeTv.text = "أهلاً ${currentUser.name}"

        binding.enter.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    NavigationDrawerActivity::class.java
                )
            )
        }
    }

}