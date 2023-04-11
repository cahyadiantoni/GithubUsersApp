package com.cahya.githubusersapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cahya.githubusersapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UserRepository): ViewModel() {

    fun getUser(username: String) = repository.getUser(username)

    fun getUserFollowers(username: String) = repository.getUserFollowers(username)

    fun getUserFollowing(username: String) = repository.getUserFollowing(username)

    fun addToFavorite(username: String) {
        viewModelScope.launch {
            repository.setUserFavorite(username, true)
        }
    }

    fun removeFromFavorite(username: String) {
        viewModelScope.launch {
            repository.setUserFavorite(username, false)
        }
    }
}