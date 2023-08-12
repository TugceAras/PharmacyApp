package com.tugcearas.pharmacyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.banner.BannerView
import com.tugcearas.pharmacyapp.data.model.Data
import com.tugcearas.pharmacyapp.databinding.FragmentHomeScreenBinding
import com.tugcearas.pharmacyapp.ui.home.adapter.PharmacyAdapter
import com.tugcearas.pharmacyapp.util.extensions.gone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var bannerView: BannerView
    private val homeViewModel: HomeVM by viewModels()
    private val homeAdapter by lazy { PharmacyAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        bannerView = binding.hwBannerView
        with(bannerView){
            adId = "testw6vs28auh3"
            bannerAdSize = BannerAdSize.BANNER_SIZE_360_57
            setBannerRefresh(10)
        }
        val adParam = AdParam.Builder().build()
        bannerView.loadAd(adParam)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    private fun createAdapter(pharmacy: List<Data>){
        binding.rvHome.adapter = homeAdapter
        homeAdapter.submitList(pharmacy)
    }

    private fun initObserve(){
        homeViewModel.getPharmacyList.observe(viewLifecycleOwner){
            it.data?.let { getData -> createAdapter(getData) }
            binding.homeProgressBar.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerView.destroy()
    }
}