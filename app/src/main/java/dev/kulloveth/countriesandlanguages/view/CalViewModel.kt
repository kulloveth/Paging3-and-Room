package dev.kulloveth.countriesandlanguages.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.kulloveth.countriesandlanguages.data.Repository

class CalViewModel(private val repository: Repository):ViewModel() {

    fun fetchAll()= repository.flow.cachedIn(viewModelScope)

    fun fetchCal()= repository.cals
    class Factory(private val repo: Repository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CalViewModel(repo) as T
        }
    }
}