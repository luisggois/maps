package com.example.wunder.carsInformation

import android.content.Intent
import com.example.wunder.main.Cars

class CarsInformationPresenter (var carsInformationView: CarsInformationContract.ViewInterface?, val carsInformationInteractor: CarsInformationInteractor) :
        CarsInformationContract.PresenterInterface {

    private val TAG = this::class.java.simpleName

    fun start(intent: Intent) {
        carsInformationView?.init() // set up toolbar
        carsInformationInteractor.handleData(intent, this) // handle the information received
    }

    fun onClickItem(car: Cars) { // handle data from the item that was clicked
        carsInformationInteractor.handleClick(car,this)
    }

    fun onDestroy() {
        carsInformationView = null
    }

    override fun setAdapter(list: ArrayList<Cars>) { // tell view to display the information we received
        carsInformationView?.apply {
            launchInformation(list)
        }
    }

    override fun setCarOnClick(list: ArrayList<Cars>) { // tell view to launch CarsLocationActivity
        carsInformationView?.apply {
            navigateToCarLocation(list)
        }
    }
}