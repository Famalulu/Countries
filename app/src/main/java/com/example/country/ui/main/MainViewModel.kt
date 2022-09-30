package com.example.country.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.country.data.repository.MainRepository
import com.example.country.util.FavoriteManager
import com.example.country.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    val favoriteManager: FavoriteManager
): ViewModel(){

    fun fetchCountries() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = mainRepository.getCountries()))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message?:"Error Occurred!", data = null))
        }
    }
}