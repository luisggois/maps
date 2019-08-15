package com.example.wunder.carsLocation

import android.content.Intent
import com.example.wunder.main.Cars
import com.google.android.gms.maps.model.LatLng

class CarsLocationInteractor {

    private val TAG = this::class.java.simpleName

    private lateinit var list: ArrayList<Cars>
    private lateinit var type: String
    private var lats:ArrayList<Double> = ArrayList()
    private var lngs:ArrayList<Double> = ArrayList()

    fun handleData(intent: Intent){

        // read the data that we received via intent
        // define the activity where the data came from (MainActivity or CarsInformationActivity)

        list = intent.getParcelableArrayListExtra("data")
        type = intent.getStringExtra("type")

        // set up the lat and lng for each car, so we can used them later on to calculate the medium location for all cars
        // in case it is only one car, it also works

        list.forEach{
            lats.add(it.lat)
            lngs.add(it.lng)
        }

    }

    fun zoomLocation(listener: CarsLocationContract.PresenterInterface){

        // get the medium location
        val Location = LatLng(lats.average(),lngs.average())

        // set the zoom for all cars
        if (type == "all"){ listener.setZoom(Location,9.75f) }

        // set the zoom for one car
        else if (type == "specific"){ listener.setZoom(Location,16f)}
    }

    fun locatePosition(listener: CarsLocationContract.PresenterInterface) {

        // set markers for all the cars
        if (type == "all") {

            list.forEach {

                // get the car location
                val carLocation = LatLng(it.lat, it.lng)

                listener.setMarkerWithoutName(carLocation, it.name)
            }
        }

        // set marker for the car that was clicked
        else if (type == "specific"){

            // get the car location
            val carLocation = LatLng(list[0].lat,list[0].lng)

            listener.setMarkerWithName(carLocation,list[0].name)
        }
    }
}
