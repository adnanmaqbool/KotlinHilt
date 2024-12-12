package com.adnan.kotlinhilt.dependency_injection

import android.content.Context
import com.adnan.kotlinhilt.config.GlobalConfig
import com.adnan.kotlinhilt.network.ApiHandler
import com.adnan.kotlinhilt.network.ApiInterface
import com.adnan.kotlinhilt.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideApiInterface(): ApiInterface {
        return ApiHandler.getApiInterface() as ApiInterface
    }
    @Singleton
    @Provides
    fun provideGlobalConfig(): GlobalConfig {
        return GlobalConfig.getInstance() as GlobalConfig
    }

    @Singleton
    @Provides
    fun provideTestModelInjection(): TestModelInjection {
        return TestModelInjection()
    }



    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }



}