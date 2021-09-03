package com.example.masrafna.ui.navigation.services

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.masrafna.data.models.ServicesModel
import com.example.masrafna.databinding.FragmentServicesBinding


private const val TAG = "ServicesFragment myTag"

class ServicesFragment : Fragment(), ServicesGridAdapter.OnItemClicked {
    private lateinit var binding: FragmentServicesBinding

    private var mContext: Context? = null
    private var servicesList = ArrayList<ServicesModel>()
    private lateinit var adapter: ServicesGridAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        getServicesList()

        adapter = ServicesGridAdapter(mContext!!, servicesList, this)
        binding.gridView.adapter = adapter
        Log.d(TAG, "onViewCreated: ${adapter.count}")
    }

    private fun getServicesList() {

        servicesList = arrayListOf(

            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.localization),
                com.example.masrafna.R.drawable.localization,
                "التوطين",
                "تحويل رواتب الموضفين من القطاع الخاص او العام"
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.loans_and_financing_id),
                com.example.masrafna.R.drawable.loans_and_financing,
                "القروض والتمويلات",
                "تعرف على الشروط والاليات وطرق التسديد "
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.deposit_accounts_id),
                com.example.masrafna.R.drawable.deposit_accounts,
                "حسابات الودائع",
                "تشمل انواع الحسابات المصرفية ومتطلبات فتح الحساب"
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.electronic_cards_id),
                com.example.masrafna.R.drawable.electronic_cards,
                "البطاقات الالكترونية",
                "تعرف علي انواع البطاقات ،متطلبات الحصول عليها"
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.letters_of_guarantee_id),
                com.example.masrafna.R.drawable.letters_of_guarantee,
                "خطابات الضمان",
                "تشمل التعرف على مفهوم خطابات الضمان \n" +
                        "والمصارف التي تقدم هذه الخدمة"
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.external_funding_icon_id),
                com.example.masrafna.R.drawable.external_funding_icon,
                "التمويلات الخارجية",
                "الاعتمادات"
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.western_union_id),
                com.example.masrafna.R.drawable.western_union,
                "التحويل عن طريق\n" +
                        "الويسترن يونين",
                ""
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.online_banking_services_id),
                com.example.masrafna.R.drawable.online_banking_services,
                "خدمات مصرفية عبر الانترنيت",
                ""
            ),
            ServicesModel(
                mContext!!.getString(com.example.masrafna.R.string.other_services_id),
                com.example.masrafna.R.drawable.other_services,
                " خدمات اخرى",
                ""
            )

        )


    }

    override fun onClicked(service: ServicesModel) {
        Log.d(TAG, "onClicked: ${service.id}")
    }


}