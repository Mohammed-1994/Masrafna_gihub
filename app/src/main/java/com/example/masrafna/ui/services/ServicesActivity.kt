package com.example.masrafna.ui.services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.masrafna.R
import com.example.masrafna.databinding.ActivityServicesBinding


private const val TAG = "ServicesActivity myTag"

class ServicesActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityServicesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityServicesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_services_activity)

//        setSupportActionBar(binding.appBarActivityServices.toolbar)

//        setupActionBarWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_services_list,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.appBarActivityServices.toolbar.setNavigationOnClickListener {
//            navController.navigateUp()
//        }

    }


}