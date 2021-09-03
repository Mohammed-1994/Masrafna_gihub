package com.example.masrafna.ui.navigation

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.masrafna.R
import com.google.android.material.navigation.NavigationView
import com.example.masrafna.databinding.ActivityNavigationDrawerBinding
import com.example.masrafna.util.Session
import com.makeramen.roundedimageview.RoundedImageView
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


private const val TAG = "NavigationDrarAct myTag"

class NavigationDrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityNavigationDrawerBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_content_navigation_drawer)

        setupHeader()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
            ), drawerLayout
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)



        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {

        val mainParms = binding.appBarNavigation.root
            .layoutParams as ViewGroup.MarginLayoutParams

        val tempMargin = mainParms.bottomMargin
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id != R.id.nav_contact &&
                destination.id != R.id.nav_notification_list &&
                destination.id != R.id.nav_article_details &&
                destination.id != R.id.nav_news &&
                destination.id != R.id.nav_news_list &&
                destination.id != R.id.nav_home
            ) {
                binding.bottomNav.visibility = GONE

                val param = binding.appBarNavigation.root
                    .layoutParams as ViewGroup.MarginLayoutParams
                param.bottomMargin = 0

            } else {
                binding.bottomNav.visibility = VISIBLE

                val param = binding.appBarNavigation.root
                    .layoutParams as ViewGroup.MarginLayoutParams
                param.bottomMargin = tempMargin
            }
        }

    }

    private fun setupHeader() {

        val headerView = binding.navView.getHeaderView(0)

        val headerName = headerView.findViewById<TextView>(R.id.header_title)

        val headerImage = headerView.findViewById<RoundedImageView>(R.id.header_imageView)
        binding.callUs.setOnClickListener {
            navController.navigate(R.id.nav_contact)
        }

        if (Session.profileResponse != null) {

            headerName.text = Session.profileResponse!!.payload.name
            Glide.with(this)
                .load(Session.profileResponse!!.payload.image)
                .placeholder(R.drawable.sticker)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(50, 0)))
                .into(headerImage)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_navigation_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        when {
            binding.drawerLayout.isOpen -> binding.drawerLayout.close()
            findNavController(R.id.nav_host_fragment_content_navigation_drawer)
                .currentDestination?.id == R.id.nav_home -> {
                Log.d(TAG, "onBackPressed: ")
                finishAffinity()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}