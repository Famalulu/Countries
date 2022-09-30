package com.example.country.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.country.R
import com.example.country.data.model.country.Country
import com.example.country.databinding.ItemCountryBinding
import com.example.country.util.ClickListener
import com.example.country.util.FavoriteManager


class FavoritesAdapter(
    private val listener: ClickListener,
    private val favoriteManager: FavoriteManager
) : RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder>() {

    private var favCountry = favoriteManager.getCountries() ?: arrayListOf()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCountryBinding.bind(itemView)

        fun bind(country: Country) {
            binding.textViewCountryName.text = country.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_country, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(favCountry[position])

        with(holder.binding) {
            itemCountry.setOnClickListener {
                listener.onClickData(favCountry[position])
            }

            imageViewFavoriteState.setImageResource(R.drawable.ic_fav_state)

            imageViewFavoriteState.setOnClickListener {
                favoriteManager.removeCountry(favCountry[position])
            }
        }
    }

    override fun getItemCount(): Int = favCountry.size

    fun setData(countries: ArrayList<Country>) {
        this.favCountry = countries
        notifyDataSetChanged()
    }
}