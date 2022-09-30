package com.example.country.data.repository

import com.example.country.data.api.ApiService
import com.example.country.data.model.country.CountryResponse
import com.example.country.data.model.detail.CountryDetailResponse
import com.example.country.util.Constants
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getCountries(): CountryResponse {
        return apiService.getCountries(Constants.API_KEY, Constants.LIMIT)
    }

    suspend fun getCountryDetail(countryCode: String): CountryDetailResponse {
        return apiService.getCountryDetail(countryCode, Constants.API_KEY)
    }
}