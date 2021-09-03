package com.example.masrafna.ui.navigation.banks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.masrafna.R
import com.example.masrafna.data.models.BankListModel
import com.example.masrafna.data.models.BankServiceModel
import com.example.masrafna.databinding.FragmentBankDetailsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity


class BankDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBankDetailsBinding
    private lateinit var bank: BankListModel.Payload.Data


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bank =
            requireArguments().getParcelable<BankListModel.Payload.Data>("bank") as BankListModel.Payload.Data

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBankDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBankServices()

        setupToolbar()

        showBankDetails()


    }

    private fun showBankDetails() {

        with(binding) {
            titleTv.text = "${binding.titleTv.text} ${bank.name}"
            with(bankDetailsSnippet) {
                aboutText.text = bank.about
                addressText.text = bank.location
                emailText.text = bank.email
                websiteText.text = bank.website
                phoneText.text = bank.phone



                Glide.with(requireContext())
                    .load(bank.logo)
                    .placeholder(R.drawable.bank_image)
                    .into(bankImage)

            }

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
            toolbar.image.visibility = View.GONE
            toolbar.title.text = bank.name

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    private fun getBankServices() {
        var type = ""
        if (bank.type == 0)
            type = "التمويلات"
        else if (bank.type == 1)
            type = "القروض"


        val list = arrayListOf(
            BankServiceModel(type, R.drawable.bank_financing),
            BankServiceModel("حسابات الودائع", R.drawable.bank_accounts),
            BankServiceModel("البطاقات الالكترونية", R.drawable.bank_cards),
            BankServiceModel("الصرافات الآلية", R.drawable.bank_atm),
            BankServiceModel("نقاط البيع", R.drawable.banks_sell_points),
            BankServiceModel("فروع المصرف", R.drawable.banks_branches),
            BankServiceModel("الخدمات الأخرى", R.drawable.banks_others)
        )
        val serviceAdapter = BankServicesAdapter(requireContext())

        serviceAdapter.setBank(bank)
        binding.banksRv.apply {
            serviceAdapter.banksList = list
            layoutManager = GridLayoutManager(requireContext(), 2)
            serviceAdapter.notifyDataSetChanged()
            adapter = serviceAdapter

        }


    }

    companion object {
        const val BANKS_LOANS = 1
        const val ISLAMIC_FINANCING = 2
    }
}