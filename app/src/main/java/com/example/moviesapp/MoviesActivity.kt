package com.example.moviesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviesapp.common.extensions.findNavController
import com.example.moviesapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController =
            supportFragmentManager.findNavController(R.id.nav_host_fragment_content_main)

        setSupportActionBar(binding.toolbar)
        configureBottomNavBar(navController)
        configureToolbar()

    }

    private fun configureBottomNavBar(navController: NavController) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
//        navController.navigate(R.id.nav_graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val visibilityMap = mapOf(
                R.id.menu_movies_list to View.VISIBLE,
                R.id.menu_favorite_list to View.VISIBLE,
            )

            bottomNavigationView.visibility = visibilityMap[destination.id] ?: View.GONE
        }
        bottomNavigationView.setupWithNavController(navController)

    }

    private fun configureToolbar() {
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_movies_list,
                R.id.menu_favorite_list,
            ),
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

}