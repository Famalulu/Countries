package com.example.country.util

import com.example.country.data.model.country.Country

interface ClickListener {
    fun onClickData (country: Country)
}