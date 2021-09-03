package com.example.masrafna.ui.services.credits

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.data.models.BankListModel
import com.example.masrafna.databinding.FragmentCreditsTypeBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.services.credits.CreditsFragment.Companion.documentary_credits
import com.example.masrafna.ui.services.credits.CreditsFragment.Companion.external_remittances
import com.example.masrafna.ui.services.localization.LocalizationListAdapter

class CreditsTypeFragment : Fragment() {

    private lateinit var binding: FragmentCreditsTypeBinding
    private var viewType = 1
    private lateinit var localizationListAdapter: LocalizationListAdapter
    private var mContext: Context? = null
    private var banksList = ArrayList<BankListModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreditsTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewType = requireArguments().getInt("view_type")

        setupToolbar()
        updateView()
        mContext = requireContext()
        getBanks()

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
            toolbar.title.visibility = View.GONE

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    private fun updateView() {
        when (viewType) {
            documentary_credits -> {
                with(binding) {
                    titleTv.text = getString(R.string.documentary_credits)
                    banksDoCredits.text = "المصارف التي تقدم الاعتمادات المستندية"
                }

            }
            external_remittances -> {
                with(binding) {
                    titleTv.text = getString(R.string.external_remittances)
                    banksDoCredits.text = "المصارف التي تقدم الحوالات الخارجية"
                }

            }

        }
    }


    private fun getBanks() {




    }


}
