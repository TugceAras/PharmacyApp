package com.tugcearas.pharmacyapp.ui.home.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tugcearas.pharmacyapp.data.model.Data
import com.tugcearas.pharmacyapp.databinding.HomeItemBinding
import com.tugcearas.pharmacyapp.util.diffutil.DiffUtilCallback
import com.tugcearas.pharmacyapp.util.extensions.click

class PharmacyAdapter: ListAdapter<Data, PharmacyAdapter.PharmacyViewHolder>(
    DiffUtilCallback<Data>(
        itemsTheSame = { oldItem, newItem ->
            oldItem == newItem
        },
        contentsTheSame = { oldItem, newItem ->
            oldItem == newItem
        }
    )
) {

     inner class PharmacyViewHolder(private val binding: HomeItemBinding): RecyclerView.ViewHolder(binding.root){

         fun bind(pharmacy: Data) = with(binding){
             tvPharmacyName.text = pharmacy.pharmacyName
             tvPharmacyAddress.text = pharmacy.address
             tvPharmacyPhone.text = pharmacy.phoneNumber
             tvPharmacyCity.text = pharmacy.city + " /"
             tvPharmacyCounty.text = pharmacy.county
             btnPhone.click {
                 val phoneNumber = pharmacy.phoneNumber
                 val intent = Intent(Intent.ACTION_DIAL)
                 intent.data = Uri.parse("tel:$phoneNumber")
                 btnPhone.context.startActivity(intent)
             }
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PharmacyViewHolder (
        HomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) = holder.bind(currentList[position])
}