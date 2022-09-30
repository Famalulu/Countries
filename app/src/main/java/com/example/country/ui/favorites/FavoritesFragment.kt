package com.example.country.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.country.R
import com.example.country.data.model.country.Country
import com.example.country.databinding.FragmentFavoritesBinding
import com.example.country.ui.main.MainViewModel
import com.example.country.util.ClickListener
import com.example.country.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), ClickListener {
    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val mainViewModel: MainViewModel by viewModels()

    private val favoriteAdapter: FavoritesAdapter by lazy {
        FavoritesAdapter(this, mainViewModel.favoriteManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }
    private fun setupUI() {
        checkEmptyState()
        mainViewModel.favoriteManager.getFavLiveData()?.observe(viewLifecycleOwner) {
            favoriteAdapter.setData(mainViewModel.favoriteManager.getCountries() ?: arrayListOf())

            checkEmptyState()
        }
        binding.recyclerViewFavCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }
    }


    override fun onClickData(country: Country) {
        val bundle = Bundle()
        bundle.putParcelable("country", country)
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_favoritesFragment_to_detailFragment, bundle)
        }
    }
    private fun checkEmptyState() {
        val countries = mainViewModel.favoriteManager.getCountries() ?: arrayListOf()

        with(binding) {
            if (countries.isEmpty()) {
                textViewEmptyState.visibility = View.VISIBLE
                recyclerViewFavCountries.visibility = View.GONE
                progressBarFav.visibility = View.GONE
            } else {
                textViewEmptyState.visibility = View.GONE
                recyclerViewFavCountries.visibility = View.VISIBLE
            }
        }
    }
}