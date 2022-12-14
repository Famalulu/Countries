package com.example.country.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.country.R
import com.example.country.data.model.country.Country
import com.example.country.databinding.FragmentMainBinding
import com.example.country.util.ClickListener
import com.example.country.util.Status
import com.example.country.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ClickListener {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private val mainViewModel: MainViewModel by viewModels()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(this, mainViewModel.favoriteManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupAPICall()
    }

    private fun setupUI() {
        binding.recyclerViewCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
        }
    }

    private fun setupAPICall() {
        mainViewModel.fetchCountries().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { countryData -> mainAdapter.setData(countryData.data) }
                    binding.recyclerViewCountries.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewCountries.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.recyclerViewCountries.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onClickData(country: Country) {
        val bundle = Bundle()
        bundle.putParcelable("country", country)
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }
}