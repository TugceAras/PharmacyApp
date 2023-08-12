package com.tugcearas.pharmacyapp.data.model

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("Adresi")
    val address: String,

    @SerializedName("EczaneAdi")
    val pharmacyName: String,

    @SerializedName("Sehir")
    val city: String,

    @SerializedName("Telefon")
    val phoneNumber: String,

    @SerializedName("ilce")
    val county: String
)