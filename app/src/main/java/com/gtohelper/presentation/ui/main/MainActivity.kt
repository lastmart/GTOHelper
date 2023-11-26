package com.gtohelper.presentation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gtohelper.R
import com.gtohelper.databinding.ActivityMainBinding
import com.gtohelper.presentation.ui.app_info.AppInfoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(MainFragment.newInstance())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(MainFragment.newInstance())
                R.id.help -> replaceFragment(AppInfoFragment.newInstance())
                else -> Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}