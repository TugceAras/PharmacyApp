package com.tugcearas.pharmacyapp.data.repo

import com.tugcearas.pharmacyapp.data.source.PharmacyApi
import com.tugcearas.pharmacyapp.util.resource.Resource
import javax.inject.Inject

class PharmacyRepo @Inject constructor(private val api: PharmacyApi) {

    suspend fun getPharmacyList() = try {
        Resource.Success(api.getList().body()?.data.orEmpty())
    } catch (e: Exception) {
        Resource.Error(e.message.orEmpty())
    }
}