package com.cahya.githubusersapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.cahya.githubusersapp.data.local.room.UserDatabase
import com.cahya.githubusersapp.data.remote.retrofit.ApiConfig
import com.cahya.githubusersapp.data.repository.UserRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

object Injection {
    fun provideRepository(ctx: Context): UserRepository {
        val preferences = SettingPreferences.getInstance(ctx.dataStore)
        val apiService = ApiConfig.getUserApi()
        val userDao = UserDatabase.getInstance(ctx).getUserDao()
        return UserRepository.getInstance(preferences, apiService, userDao)
    }
}