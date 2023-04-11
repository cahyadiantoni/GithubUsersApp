package com.cahya.githubusersapp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cahya.githubusersapp.data.repository.UserRepository
import com.cahya.githubusersapp.utils.Injection

class ViewModelFactory private constructor(private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
        {
            return MainViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java))
        {
            return DetailViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(SearchViewModel::class.java))
        {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}