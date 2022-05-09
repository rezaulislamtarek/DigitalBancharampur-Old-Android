package com.diatomicsoft.digitalbancharampur.ui.car

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diatomicsoft.digitalbancharampur.model.repository.CarRepository

class CarViewModelFactory(val repository: CarRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CarViewModel(repository) as T
    }
}