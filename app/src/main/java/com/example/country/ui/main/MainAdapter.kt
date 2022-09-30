package com.example.country.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.country.R
import com.example.country.data.model.country.Country
import com.example.country.databinding.ItemCountryBinding
import com.example.country.util.ClickListener
import com.example.country.util.FavoriteManager


class MainAdapter constructor(
    private val listener: ClickListener,
    private val favoriteManager: FavoriteManager
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>()
{
    private var countries: ArrayList<Country> = ArrayList()

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCountryBinding.bind(itemView)
        fun bind(country: Country) {
            binding.textViewCountryName.text = country.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_country, parent,
                false
            )
        )

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(countries[position])
        holder.binding.itemCountry.setOnClickListener {
            listener.onClickData(countries[position])
        }
        val isFav = favoriteManager.countryInFav(countries[position])

        if (isFav) {
            holder.binding.imageViewFavoriteState.setImageResource(R.drawable.ic_fav_state)
        } else {
            holder.binding.imageViewFavoriteState.setImageResource(R.drawable.ic_fav_empty_state)
        }

        holder.binding.imageViewFavoriteState.setOnClickListener {
            val isInFav = favoriteManager.countryInFav(countries[position])

            if (isInFav) {
                favoriteManager.removeCountry(countries[position])
            } else {
                favoriteManager.setCountry(countries[position])
            }
            notifyDataSetChanged()
        }

    }

    fun setData(countries: List<Country>) {
        this.countries.apply {
            clear()
            addAll(countries)
        }
    }
}