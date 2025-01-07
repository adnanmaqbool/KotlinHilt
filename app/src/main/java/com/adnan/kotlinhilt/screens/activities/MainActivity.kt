package com.adnan.kotlinhilt.screens.activities

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.adnan.kotlinhilt.R
import com.adnan.kotlinhilt.config.GlobalConfig
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var globalConfig: GlobalConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // above will assign it
        globalConfig.navController = navHostFragment.navController
        globalConfig.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            // do some task when fragment changes ?

            when (destination.id) {
                R.id.chatFragment, R.id.settingFragment, R.id.profileFragment -> {
                    bottomNavigation.visibility = View.VISIBLE
                }


                else -> {
                    bottomNavigation.visibility = View.GONE
                }
            }

        }
        bottomNavigation.setupWithNavController(globalConfig.navController)




    }

}

