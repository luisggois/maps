package com.example.wunder.carsInformation

import android.content.Intent
import com.example.wunder.main.Cars

class CarsInformationInteractor {

    fun handleData(intent: Intent, listener: CarsInformationContract.PresenterInterface){

        // read the data that we received via intent, from the MainActivity
        val list:ArrayList<Cars> = intent.getParcelableArrayListExtra("data")

        // tell presenter to tell view to set the list view adapter with the input we received
        listener.setAdapter(list)
    }

    fun handleClick(car: Cars, listener: CarsInformationContract.PresenterInterface){

        // prepare data from the item that was clicked
        // send its object inside an array to match the parcelable implementation we created previously
        val list:ArrayList<Cars> = ArrayList()
        list.add(car)

        // tell presenter to tell view to send this data via intent, to the CarsLocationActivity
        listener.setCarOnClick(list)
    }

}
