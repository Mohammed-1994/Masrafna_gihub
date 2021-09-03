package com.example.masrafna.ui.navigation.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masrafna.R
import com.example.masrafna.api.NetworkStatus
import com.example.masrafna.data.models.NewsListModel
import com.example.masrafna.databinding.FragmentNewsListBinding
import com.example.masrafna.ui.navigation.NavigationDrawerActivity

private const val TAG = "NewsListFragment myTag"

class NewsListFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()
    private var binding: FragmentNewsListBinding? = null


    private var page = 1
    private var isLoading = false

    private var lastPage = 0

    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        with(newsViewModel) {

            networkStatus.observe(this@NewsListFragment) {
                binding!!.progressBar.visibility =
                    if (it == NetworkStatus.LOADING) VISIBLE else GONE
            }

            newsListResponse.observe(this@NewsListFragment, {
                populateResult(it)
            })
        }
    }

    private fun populateResult(response: NewsListModel?) {


        if (response != null) {
            Log.d(TAG, "populateResult: ${response.payload.data.size}")
            lastPage = response.payload.meta.lastPage
            isLoading = false
            if (::newsAdapter.isInitialized) {

                if (page == 1) {
                    newsAdapter.newsListList.clear()
                    binding!!.newsRv.adapter = newsAdapter
                }
                newsAdapter.newsListList.addAll(response.payload.data)

            } else {
                Log.d(TAG, "populateResult: adapter is not init")
                newsAdapter = NewsAdapter(requireContext())
                newsAdapter.newsListList = response.payload.data.toMutableList()
                binding!!.newsRv.adapter = newsAdapter
            }
            newsAdapter.notifyDataSetChanged()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        binding = FragmentNewsListBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)

        page = 1
        getPage()

        binding!!.newsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {

                    val manager = binding!!.newsRv.layoutManager!! as LinearLayoutManager

                    val visibleItemCount = manager.childCount
                    val pastVisibleItem = manager.findFirstCompletelyVisibleItemPosition()

                    val total = newsAdapter.itemCount

                    if (!isLoading) {
                        if (visibleItemCount + pastVisibleItem >= total) {

                            page++
                            Log.d(TAG, "onScrolled: $lastPage, $pastVisibleItem")
                            if (page <= lastPage) {
                                getPage()
                            }

                        }

                    }
                }
            }
        })
        setupToolbar()

    }

    private fun getPage() {
        Log.d(TAG, "getPage: $page")
        isLoading = true
        newsViewModel.getNews(page)
    }


    private fun setupToolbar() {

        with(binding!!) {


            toolbar.drawerIcon.setOnClickListener {
                (requireContext() as NavigationDrawerActivity)
                    .binding.drawerLayout.open()
            }

            toolbar.navigateUp.setOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.title.text = getString(R.string.news)

            if (!resources.getBoolean(R.bool.is_right_to_left)) {
                toolbar.navigateUp.rotation = 180f
            }
        }
    }


}