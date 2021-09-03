package com.example.masrafna.ui.navigation.terms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.databinding.FragmentTermsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.navigation.info.InfoViewModel

class TermsFragment : Fragment() {

    private lateinit var binding: FragmentTermsBinding
    private val infoViewModel: InfoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(infoViewModel) {
            networkStatus.observe(this@TermsFragment) {
                binding.progressBar.visibility =
                    if (it == com.example.masrafna.api.NetworkStatus.LOADING) android.view.View.VISIBLE else android.view.View.GONE
            }

            conditionsResponse.observe(this@TermsFragment, {
                if (it != null)
                    binding.firstText.text = it.payload.text
            })

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTermsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        infoViewModel.getConditions()
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
            toolbar.title.text = getString(R.string.terms)
            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

}