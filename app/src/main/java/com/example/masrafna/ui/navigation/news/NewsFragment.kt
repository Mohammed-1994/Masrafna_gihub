package com.example.masrafna.ui.navigation.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.NewsDetails
import com.example.masrafna.databinding.FragmentNewsBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import org.ocpsoft.prettytime.PrettyTime

private const val TAG = "NewsFragment myTag"

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = requireArguments().getString("id").toString()

        with(newsViewModel) {
            networkStatus.observe(this@NewsFragment) {
                binding.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) View.VISIBLE else View.GONE
            }

            newsDetailsResponse.observe(this@NewsFragment, {
                populateNewsDetails(it)
            })

        }
    }

    private fun populateNewsDetails(details: NewsDetails?) {
        if (details != null) {
            with(binding) {
                titleTv.text = details.payload.title
                date.text = PrettyTime().format(details.payload.createdAt)
                desc.text = details.payload.content

                Glide.with(requireContext())
                    .load(details.payload.image)
                    .placeholder(R.drawable.sticker)
                    .into(newsImage)

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        newsViewModel.getNewsDetails(id)
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
            toolbar.title.visibility = INVISIBLE
            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }


}