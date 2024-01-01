package com.gtohelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.gtohelper.theme.GTOHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTOHelperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppHavHost(navController)
                }
            }
        }
    }
}


//@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        window.statusBarColor = Color.parseColor("#BCBCBC")
//
//        initNavController()
//        initBottomNavigation()
//        initToolbar()
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
//
//    private fun initNavController() {
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.findNavController()
//    }
//
//    private fun initBottomNavigation() {
//        binding.bottomNavigationView.setupWithNavController(navController)
//    }
//
//    private fun initToolbar() {
//        setSupportActionBar(binding.mainActivityToolbar)
//        val topLevelDestinations = setOf(R.id.competitionListFragment, R.id.helpFragment)
//        val appBarConfiguration = AppBarConfiguration(topLevelDestinations)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//    }
//}