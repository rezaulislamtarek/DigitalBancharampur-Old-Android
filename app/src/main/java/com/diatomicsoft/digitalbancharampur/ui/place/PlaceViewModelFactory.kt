package com.diatomicsoft.digitalbancharampur.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diatomicsoft.digitalbancharampur.model.repository.PlaceRepository

class PlaceViewModelFactory(val repository: PlaceRepository, val type: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceViewModel(repository, type) as T
    }
}