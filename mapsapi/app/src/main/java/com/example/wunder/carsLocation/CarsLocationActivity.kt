package com.example.wunder.carsLocation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.wunder.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_cars_location.*

class CarsLocationActivity : AppCompatActivity(), CarsLocationContract.ViewInterface, OnMapReadyCallback{

    private val TAG = this::class.java.simpleName

    private lateinit var mapView: GoogleMap

    private val presenter = CarsLocationPresenter(this, CarsLocationInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars_location)

        presenter.start(intent) // understand which information we received via intent and afterwards set up the mapView view to handle it

    }

    override fun onDestroy() { // avoid leaking the activity with long-running tasks
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun init() { // set up toolbar properties
        setSupportActionBar(my_toolbar)
        supportActionBar?.title="Cars Location"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean { // go back to previous activity
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun setUpMap() { // acquired the map
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) { // wait for map to be ready and then set execution
        mapView = googleMap
        mapView.setOnMapLoadedCallback {
            mapView.uiSettings.isZoomControlsEnabled = true // enable zoom
            presenter.setZoomLocation() // set up zoom
            presenter.locate() // set up location(s)
        }
    }

    override fun addMarkerWithoutName(position: LatLng, name: String) { // add marker without name
        mapView.addMarker(MarkerOptions().position(position).title(name))
    }

    override fun addMarkerWithName(position: LatLng, name: String) { // add marker with name
        mapView.addMarker(MarkerOptions().position(position).title(name)).showInfoWindow()
    }

    override fun addZoom(position: LatLng,scale: Float) { // zoom mapView according to specific scale
        mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(position,scale))
    }

}