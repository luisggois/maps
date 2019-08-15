package com.example.wunder.carsLocation

import android.content.Intent
import com.google.android.gms.maps.model.LatLng

class CarsLocationPresenter (var carsLocationView: CarsLocationContract.ViewInterface?, val carsLocationInteractor: CarsLocationInteractor) :
        CarsLocationContract.PresenterInterface {

    private val TAG = this::class.java.simpleName

    fun start(intent: Intent) {
        carsLocationView?.init() // set up toolbar
        carsLocationInteractor.handleData(intent) // handle the information received
        carsLocationView?.setUpMap() // set up map
    }

    fun setZoomLocation(){
        carsLocationInteractor.zoomLocation(this) // set up the zoom we need
    }

    fun locate() {
        carsLocationInteractor.locatePosition(this) // set up the marker(s) we need
    }

    fun onDestroy() {
        carsLocationView = null
    }

    override fun setMarkerWithoutName(position: LatLng, name: String) { // tell view to set a marker without name
        carsLocationView?.apply {
            addMarkerWithoutName(position,name)
        }
    }

    override fun setMarkerWithName(position: LatLng, name: String) { // tell view to set a marker with name
        carsLocationView?.apply {
            addMarkerWithName(position,name)
        }
    }

    override fun setZoom(position: LatLng, scale: Float) { // tell view to set zoom
        carsLocationView?.apply {
            addZoom(position,scale)
        }
    }

}