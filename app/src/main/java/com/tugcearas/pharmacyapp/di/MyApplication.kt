package com.tugcearas.pharmacyapp.di

import android.app.Application
import com.huawei.hms.ads.HwAds
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.maps.MapsInitializer
import com.tugcearas.pharmacyapp.util.constants.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        HwAds.init(this)

        // Set the API key (map kit)
        MapsInitializer.initialize(this)
        MapsInitializer.setApiKey(Constants.APP_GALLERY_PROJECT_API)

        // init analytic
        HiAnalytics.getInstance(this)
    }
}