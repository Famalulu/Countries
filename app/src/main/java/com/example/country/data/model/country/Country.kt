package com.example.country.data.model.country

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("code")
    val code: String? = null,
) : Parcelable