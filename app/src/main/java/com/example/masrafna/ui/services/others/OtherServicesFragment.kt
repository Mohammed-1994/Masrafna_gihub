package com.example.masrafna.ui.services.others

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.core.view.iterator
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.databinding.FragmentOtherServicesBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity

class OtherServicesFragment : Fragment() {


    private lateinit var binding: FragmentOtherServicesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherServicesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupToolbar()
        val buttonClicked = View.OnClickListener {
            val title = (it as Button).text
            val bundle = bundleOf("id" to title)
            findNavController().navigate(
                R.id.action_nav_otherServicesFragment_to_nav_banksServiceFragment,
                bundle
            )
        }

        with(binding) {
            for (button in this.servicesList)
                button.setOnClickListener(buttonClicked)
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
            toolbar.title.text = getString(R.string.other_bank_services)
            toolbar.image.setImageResource(R.drawable.other_services_dark_icon)

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

}