package com.adnan.kotlinhilt.config

import androidx.navigation.NavController


class GlobalConfig {

    lateinit var navController: NavController
    var CAT_ID = 32

    companion object {
        private var instance: GlobalConfig? = null



        fun getInstance(): GlobalConfig {
            if (instance == null) {
                instance = GlobalConfig()
            }
            return instance as GlobalConfig
        }
    }


}