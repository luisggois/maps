package com.example.wunder.carsInformation

import com.example.wunder.main.Cars

interface CarsInformationContract {

    interface ViewInterface {
        fun init()
        fun launchInformation(list: ArrayList<Cars>)
        fun navigateToCarLocation(list: ArrayList<Cars>)
    }

    interface PresenterInterface {
        fun setAdapter(list: ArrayList<Cars>)
        fun setCarOnClick(list: ArrayList<Cars>)
    }
}