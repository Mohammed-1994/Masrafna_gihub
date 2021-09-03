package com.example.masrafna.ui.navigation.info

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.ServicesModel
import com.example.masrafna.databinding.FragmentInfoBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import com.example.masrafna.ui.navigation.services.ServicesGridAdapter

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private var mContext: Context? = null

    private val infoViewModel: InfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(infoViewModel){
            networkStatus.observe(this@InfoFragment){
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) VISIBLE else GONE
            }

            aboutResponse.observe(this@InfoFragment,{
                if (it != null)
//                binding.text.text = it.payload.text
                binding.text.text =
                    Html.fromHtml(it.payload.text, Html.FROM_HTML_MODE_COMPACT)
            })

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        setupToolbar()

        infoViewModel.getInfo()
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
            toolbar.title.text = getString(R.string.meet_masrafna)

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

}