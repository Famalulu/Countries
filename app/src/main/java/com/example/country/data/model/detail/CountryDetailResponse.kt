package com.example.country.data.model.detail

import com.google.gson.annotations.SerializedName

data class CountryDetailResponse (

    @SerializedName("data")
    val data: CountryDetail
)