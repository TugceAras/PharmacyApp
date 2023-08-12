# <p align="center"> PharmacyApp - "Eczanem" 💊 </p>

![Pharmacy App](https://github.com/TugceAras/PharmacyApp/assets/79931228/ac90e371-a8e7-428b-81d3-d25eb7851b52)

## 📸 Screenshots

<br>

<p align="center">
  <img src="https://github.com/TugceAras/PharmacyApp/assets/79931228/df99e43b-4bb5-4a81-aa2f-528d9a1239d6"/>
  <img src="https://github.com/TugceAras/PharmacyApp/assets/79931228/c933f9f1-8fcf-4d75-85bd-47c616f375f5"/> 
  <img src="https://github.com/TugceAras/PharmacyApp/assets/79931228/baffcb22-63de-458a-b8d8-c1e98307cf31"/> <br>
  <img src="https://github.com/TugceAras/PharmacyApp/assets/79931228/71608c97-134a-4544-bcfc-484ca7e425f0)"/> 
  <img src="https://github.com/TugceAras/PharmacyApp/assets/79931228/4509214e-f71e-4198-9286-b085d48f51ef"/> 

</p>

<br>

## 🎯 Goal 
- To learn how to integrate and use Huawei HMS Core kits. That's why I developed the Pharmacy application. <br>
    - <strong> Project Goal : </strong> <br>
      To inform the user about the duty pharmacies. At the same time, taking the user's location and showing nearby pharmacies on the map.

<br>

## 👇 Used HMS Core Kits
- Account Kit
- ADS Kit
- Analytics Kit
- Map Kit
- Location Kit
- Site Kit

> <strong>  💡  NOTE : </strong> If you want to take a look at these kits I use, you can check this site 👇
>
> https://developer.huawei.com/consumer/en/hms/  
<br>

🔴 Where did I use these kits?
- <strong> Login Screen </strong> ---> I used Account kit
- <strong> Home Screen </strong> ---> I used ADS Kit
- <strong> Map Screen </strong>
    - Map Kit : for create a map <br>
    - Location Kit : for get user current location
- <strong> Navigation Screen </strong>
  - Site Kit : for get nearby pharmacies

## 👇 Used Technologies
- MVVM 
- Hilt | DI
- Coroutines
- Retrofit
- LiveData
- Navigation Component
- ViewBinding
- SSP\SDP
- Lottie

  <br>

  
## :pencil2: Dependency
```
    dependencies {

    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    // Navigation
    def nav_version = "2.6.0"
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

    // Hilt
    implementation "com.google.dagger:hilt-android:2.44"
    kapt "com.google.dagger:hilt-compiler:2.44"

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    // Coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Coroutine Scope
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'

    // SSP-SDP
    implementation 'com.intuit.sdp:sdp-android:1.1.0'
    implementation 'com.intuit.ssp:ssp-android:1.1.0'

    // Lottie
    def lottieVersion = "3.4.0"
    implementation "com.airbnb.android:lottie:$lottieVersion"

    // Account Kit
    implementation 'com.huawei.hms:hwid:6.11.0.300'
    implementation 'com.huawei.hms:hmscoreinstaller:6.11.0.301'

    // Ads Kit
    implementation 'com.huawei.hms:ads-prime:3.4.65.300'

    // Map Kit
    implementation 'com.huawei.hms:maps:6.11.0.304'
    implementation 'com.huawei.hms:maps-basic:6.11.0.304'

    // Location Kit
    implementation 'com.huawei.hms:location:6.11.0.301'

    // Site Kit
    implementation 'com.huawei.hms:site:6.5.1.301'

    // Analytics Kit
    implementation 'com.huawei.hms:hianalytics:6.10.0.302'
```

<br>

## 👇 API
- https://www.nosyapi.com/
