package com.example.masrafna.ui.services.credits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.databinding.FragmentCreditsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity


class CreditsFragment : Fragment() {

    private lateinit var binding: FragmentCreditsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        binding = FragmentCreditsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        binding.documentaryCredits.setOnClickListener {
            val bundle = bundleOf("view_type" to documentary_credits)
            findNavController().navigate(
                R.id.action_nav_creditFragment_to_nav_creditsTypeFragment,
                bundle
            )
        }

        binding.externalRemittances.setOnClickListener {
            val bundle = bundleOf("view_type" to external_remittances)
            findNavController().navigate(
                R.id.action_nav_creditFragment_to_nav_creditsTypeFragment,
                bundle
            )
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
            toolbar.title.text = getString(R.string.external_funding)
            toolbar.image.setImageResource(R.drawable.external_financing_dark_icon)

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }


    companion object {
        const val documentary_credits = 1
        const val external_remittances = 2
    }

}