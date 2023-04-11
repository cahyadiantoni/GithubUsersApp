package com.cahya.githubusersapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.cahya.githubusersapp.data.repository.UserRepository

class SearchViewModel(private val repository: UserRepository): ViewModel() {
    fun getSearchUsers(query: String) = repository.getSearchUsers(query)
}