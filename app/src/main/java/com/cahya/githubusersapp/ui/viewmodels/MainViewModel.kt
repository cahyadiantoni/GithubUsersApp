package com.cahya.githubusersapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cahya.githubusersapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {
    fun getUsers() = repository.getUsers()

    fun getFavoriteUsers() = repository.getFavoriteUsers()

    fun getThemeSetting(): LiveData<Boolean> = repository.getThemeSetting().asLiveData()

    fun saveThemeSetting(newSetting: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(newSetting)
        }
    }
}