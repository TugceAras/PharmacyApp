package com.tugcearas.pharmacyapp.util.extensions

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.click(func: () -> Unit){
    this.setOnClickListener {
        func()
    }
}

fun Context.toastMessage(message:String) =
    Toast.makeText(this, message,Toast.LENGTH_SHORT).show()

fun Context.toastMessageLong(message:String) =
    Toast.makeText(this, message,Toast.LENGTH_LONG).show()