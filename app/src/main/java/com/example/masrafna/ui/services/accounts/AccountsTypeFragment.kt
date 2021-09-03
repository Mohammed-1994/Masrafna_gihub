package com.example.masrafna.ui.services.accounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import androidx.core.os.bundleOf
import androidx.core.view.allViews
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.data.models.AccountModel
import com.example.masrafna.data.models.CardsModel
import com.example.masrafna.databinding.FragmentAccountsTypeBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity


class AccountsTypeFragment : Fragment() {

    private lateinit var binding: FragmentAccountsTypeBinding
    private lateinit var accountModel: AccountModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountsTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupToolbar()
        val accounts = ArrayList<AccountModel>()
        accounts.add(AccountModel(0, getString(R.string.saving_account), "", ""))
        accounts.add(AccountModel(0, getString(R.string.fixed_deposit_accounts), "", ""))
        accounts.add(AccountModel(0, getString(R.string.time_deposit_accounts), "", ""))
        accounts.add(AccountModel(0, getString(R.string.current_accounts), "", ""))
        accounts.add(AccountModel(0, getString(R.string.certificate_deposit), "", ""))

        val cardClicked = View.OnClickListener {
            findNavController().navigate(R.id.action_nav_accounts_type_to_nav_accountDetails)
        }


        for (card in binding.cardsList.allViews) {
            card.setOnClickListener(cardClicked)
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
            toolbar.title.text = "الحسابات المصرفية"
            toolbar.image.setImageResource(R.drawable.saving_dark_icon)
            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

}