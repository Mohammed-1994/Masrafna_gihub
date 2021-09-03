package com.example.masrafna.ui.services.localization

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.BankLocalizationModel
import com.example.masrafna.databinding.FragmentBankLocalizationBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity

private const val TAG = "BankLocalizationFragmen myTag"

class BankLocalizationFragment : Fragment() {


    private lateinit var binding: FragmentBankLocalizationBinding
    private var id = ""
    private var name = ""
    private val localViewModel: LocalViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = requireArguments().getString("id").toString()
        name = requireArguments().getString("name").toString()

        with(localViewModel) {
            networkStatus.observe(this@BankLocalizationFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            bankLocalResponse.observe(this@BankLocalizationFragment, {
                populateResult(it)
            })
        }

    }

    private fun populateResult(bank: BankLocalizationModel?) {
        if (bank != null) {
            with(binding) {
                desc.text = bank.payload.text
                title1.text = "التوطين في $name"

            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBankLocalizationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        localViewModel.getBankLocalizations(id)
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
            toolbar.image.setImageResource(R.drawable.localization_dark_icon)
            toolbar.title.text = getString(R.string.localization)

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }


}