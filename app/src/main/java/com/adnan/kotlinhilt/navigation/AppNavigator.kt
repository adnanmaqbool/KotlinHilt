package com.adnan.kotlinhilt.navigation

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import com.adnan.kotlinhilt.R
import com.adnan.kotlinhilt.config.GlobalConfig


class AppNavigator {
    companion object {
        private const val TAG = "Navigator"
        private fun getController(): NavController {
            return GlobalConfig.getInstance().navController
        }

        fun getCurrentDestinationId(): Int? {
            return getController().currentDestination?.id
        }

        fun moveBackToPreviousFragment() {
            getController().popBackStack()
        }

        fun navigateToSplash(args: Bundle = Bundle()) {
            Log.i(TAG, "navigateToLogin: $args")
            val navAction = NavAction(R.id.splashFragment)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(getCurrentDestinationId()!!, true).build()
            navAction.navOptions = navOptions

            val destination: NavDestination? = getCurrentDestinationId()?.let {
                getController().graph.findNode(it)
            }
            if (destination != null) {
                destination.putAction(R.id.splash_fragment_action, navAction)
                getController().navigate(R.id.splash_fragment_action, args)
            }
        }

        fun navigateToHome(args: Bundle = Bundle()) {
            Log.i(TAG, "navigateToHome: $args")
            val navAction = NavAction(R.id.homeFragment)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(getCurrentDestinationId()!!, true).build()
            navAction.navOptions = navOptions

            val destination: NavDestination? = getCurrentDestinationId()?.let {
                getController().graph.findNode(it)
            }
            if (destination != null) {
                destination.putAction(R.id.home_fragment_action, navAction)
                getController().navigate(R.id.home_fragment_action, args)
            }
        }

        fun navigateToSetting(args: Bundle = Bundle()) {
            Log.i(TAG, "navigateToSetting: $args")
            val navAction = NavAction(R.id.settingFragment)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(getCurrentDestinationId()!!, false).build()
            navAction.navOptions = navOptions

            val destination: NavDestination? = getCurrentDestinationId()?.let {
                getController().graph.findNode(it)
            }
            if (destination != null) {
                destination.putAction(R.id.setting_fragment_action, navAction)
                getController().navigate(R.id.setting_fragment_action, args)
            }
        }




    }
}
