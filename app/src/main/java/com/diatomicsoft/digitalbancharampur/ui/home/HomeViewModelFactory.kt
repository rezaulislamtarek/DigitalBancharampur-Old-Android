package com.diatomicsoft.digitalbancharampur.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diatomicsoft.digitalbancharampur.model.repository.HomeRepository

class HomeViewModelFactory(private val repository: HomeRepository): ViewModelProvider.NewInstanceFactory (){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}