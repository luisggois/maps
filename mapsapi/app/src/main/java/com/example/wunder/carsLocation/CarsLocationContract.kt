package com.example.wunder.carsLocation

import com.google.android.gms.maps.model.LatLng

interface CarsLocationContract {

    interface ViewInterface {
        fun init()
        fun setUpMap()
        fun addMarkerWithoutName(position: LatLng, name: String)
        fun addMarkerWithName(position: LatLng, name: String)
        fun addZoom(position: LatLng,scale: Float)
    }

    interface PresenterInterface {
        fun setMarkerWithoutName(position: LatLng, name: String)
        fun setMarkerWithName(position: LatLng, name: String)
        fun setZoom(position: LatLng, scale: Float)
    }
}