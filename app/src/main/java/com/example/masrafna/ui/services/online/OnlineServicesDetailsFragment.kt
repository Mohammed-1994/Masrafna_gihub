package com.example.masrafna.ui.services.online

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.OnlineServiceDetails
import com.example.masrafna.databinding.FragmentOnlineServicesDetailsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity

class OnlineServicesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOnlineServicesDetailsBinding
    private val onlineViewModel: OnlineViewModel by viewModels()

    var bankName = ""
    var bankId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bankId = requireArguments().getString("id").toString()
        bankName = requireArguments().getString("name").toString()


        with(onlineViewModel) {
            networkStatus.observe(this@OnlineServicesDetailsFragment, {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            })

            onlineServiceDetails.observe(this@OnlineServicesDetailsFragment, {
                populateResult(it)
            })
        }
    }

    private fun populateResult(response: OnlineServiceDetails?) {
        if (response != null){
            binding.desc.text = response.payload.text
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnlineServicesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        with(binding) {
            title1.text = " خدمات الانترنت المصرفية عبر $bankName"
        }

        onlineViewModel.getOnlineServicesDetails(bankId)
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
            toolbar.title.text = getString(R.string.online_service)
            toolbar.image.visibility = GONE

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

}