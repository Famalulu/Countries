package com.example.country.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.country.R
import com.example.country.data.model.country.Country
import com.example.country.databinding.FragmentDetailBinding
import com.example.country.util.Constants
import com.example.country.util.Status
import com.example.country.util.loadUrl
import com.example.country.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val detailViewModel: DetailViewModel by viewModels()
    private var country: Country? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        country = arguments?.getParcelable("country")

        setupAPICall()
        favIconSet()
        clickListeners()
    }

    private fun setupAPICall() {

        detailViewModel.favoriteManager.getFavLiveData()?.observe(viewLifecycleOwner) {
            favIconSet()
        }

        detailViewModel.fetchCountryDetail(country?.code.orEmpty())
            .observe(viewLifecycleOwner) { countryDetailResponse ->
                when (countryDetailResponse.status) {
                    Status.SUCCESS -> {
                        with(binding) {
                            imageViewFlag.loadUrl(countryDetailResponse.data?.data?.flagImageUri?.replace("http", "https"))
                            textViewTitleToolbar.text = resources.getString(
                                R.string.country_name,
                                countryDetailResponse.data?.data?.name
                            )
                            textViewCode.text =
                                resources.getString(R.string.countryCode, country?.code)
                            buttonWiki.setOnClickListener {
                                val queryUrl: Uri =
                                    Uri.parse("${Constants.WIKI_DATA_URL}${countryDetailResponse.data?.data?.wikiDataId}")
                                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                                context?.startActivity(intent)
                            }
                            progress.visibility = View.GONE
                            linearLayoutRoot.visibility = View.VISIBLE
                        }
                    }
                    Status.LOADING -> {
                        with(binding) {
                            progress.visibility = View.VISIBLE
                            linearLayoutRoot.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        binding.linearLayoutRoot.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            countryDetailResponse.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
    }


    private fun clickListeners() {
        with(binding) {
            imageViewBackButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

            imageViewFavoriteButton.setOnClickListener {

                country?.let {
                    val isInFav = detailViewModel.favoriteManager.countryInFav(it)

                    if (isInFav)
                        detailViewModel.favoriteManager.removeCountry(it)
                    else
                        detailViewModel.favoriteManager.setCountry(it)
                }
            }
        }
    }
    private fun favIconSet() {
        country?.let {
            val isInFav = detailViewModel.favoriteManager.countryInFav(it)

            if(isInFav)
                binding.imageViewFavoriteButton.setImageResource(R.drawable.ic_fav_state_white)
            else
                binding.imageViewFavoriteButton.setImageResource(R.drawable.ic_fav_empty_state_white)
        }
    }



}