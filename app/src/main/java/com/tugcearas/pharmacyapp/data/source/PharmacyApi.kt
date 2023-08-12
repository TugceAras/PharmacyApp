package com.tugcearas.pharmacyapp.data.source

import com.tugcearas.pharmacyapp.data.model.PharmacyModel
import com.tugcearas.pharmacyapp.util.constants.Constants
import retrofit2.Response
import retrofit2.http.GET

interface PharmacyApi {

    @GET(Constants.GET_LIST)
    suspend fun getList() : Response<PharmacyModel>
}