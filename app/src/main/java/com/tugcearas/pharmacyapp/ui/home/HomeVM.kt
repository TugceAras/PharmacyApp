package com.tugcearas.pharmacyapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugcearas.pharmacyapp.data.model.Data
import com.tugcearas.pharmacyapp.data.repo.PharmacyRepo
import com.tugcearas.pharmacyapp.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val pharmacyRepo: PharmacyRepo): ViewModel() {

    val getPharmacyList: MutableLiveData<Resource<List<Data>>> = MutableLiveData()

    init {
        getList()
    }

    private fun getList() = viewModelScope.launch {
        getPharmacyList.postValue(handleResponse(pharmacyRepo.getPharmacyList()))
    }

    private fun handleResponse(response:Resource<List<Data>>) = when(response){
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(response.data.orEmpty())
        is Resource.Error -> Resource.Error(response.message.orEmpty())
    }
}