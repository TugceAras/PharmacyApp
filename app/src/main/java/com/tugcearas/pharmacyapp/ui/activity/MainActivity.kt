package com.tugcearas.pharmacyapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.HwAds
import com.huawei.hms.ads.banner.BannerView
import com.tugcearas.pharmacyapp.R
import com.tugcearas.pharmacyapp.databinding.ActivityMainBinding
import com.tugcearas.pharmacyapp.util.extensions.gone
import com.tugcearas.pharmacyapp.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)

        navController.addOnDestinationChangedListener{_ , destination, _ ->
            when(destination.id){
                R.id.splashScreen -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    binding.bottomNavView.gone()
                }
                R.id.loginScreen -> {
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)
                }
                R.id.mapScreen -> {
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)
                }
                else -> {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)
                    binding.bottomNavView.visible()
                }
            }
        }
    }
}