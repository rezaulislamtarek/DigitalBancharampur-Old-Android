package com.diatomicsoft.digitalbancharampur.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diatomicsoft.digitalbancharampur.model.repository.AuthRepository

class AuthViewModelFactory(val repository: AuthRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}