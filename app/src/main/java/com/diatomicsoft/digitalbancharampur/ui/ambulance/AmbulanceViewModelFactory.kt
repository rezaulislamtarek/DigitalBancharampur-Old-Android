package com.diatomicsoft.digitalbancharampur.ui.ambulance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diatomicsoft.digitalbancharampur.model.repository.AmbulanceRepository

class AmbulanceViewModelFactory(val repository: AmbulanceRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AmbulanceViewModel(repository) as T
    }

}