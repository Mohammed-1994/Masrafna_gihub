package com.example.masrafna.ui.navigation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.masrafna.R
import com.example.masrafna.data.models.ArticleDetails
import com.example.masrafna.databinding.FragmentNotificationBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity
import org.ocpsoft.prettytime.PrettyTime

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: HomeViewModel by viewModels()
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = requireArguments().getString("id").toString()

        with(viewModel) {
            networkStatus.observe(this@ArticleFragment) {
                binding.progressBar.visibility =
                    if (it == com.example.masrafna.api.NetworkStatus.LOADING) android.view.View.VISIBLE else android.view.View.GONE
            }

            articleDetailsResponse.observe(this@ArticleFragment, {
                showArticleDetails(it)
            })

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()

        viewModel.getArticleDetails(id)

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
            toolbar.title.visibility = View.INVISIBLE
            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }

    private fun showArticleDetails(article: ArticleDetails?) {

        if (article != null) {
            with(binding) {
                titleTv.text = article.payload.title
                date.text = PrettyTime().format(article.payload.createdAt)
                desc.text = article.payload.content

                Glide.with(requireContext())
                    .load(article.payload.image)
                    .placeholder(R.drawable.sticker)
                    .into(notificationImage)

            }
        }


    }

}