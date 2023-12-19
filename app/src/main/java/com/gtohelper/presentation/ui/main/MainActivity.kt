package com.gtohelper.presentation.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gtohelper.R
import com.gtohelper.data.database.AppDatabase
import com.gtohelper.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#BCBCBC")

        initNavController()
        initBottomNavigation()
        initToolbar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
        /*binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mainFragment -> {
                 //   navController.navigate(R.layout.fragment_main)
                 //   navController.navigate(R.id.action)
                    true
                }

                R.id.appInfoFragment -> {
                    true
                }

                else -> false
            }
        }*/
    }

    private fun initToolbar() {
        setSupportActionBar(binding.mainActivityToolbar)
        setupActionBarWithNavController(navController)
    }
}