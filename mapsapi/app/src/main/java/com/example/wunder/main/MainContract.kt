package com.example.wunder.main

interface MainContract {

    interface ViewInterface {
        fun navigateToCarsInformation(list: ArrayList<Cars>)
        fun navigateToCarsLocation(list: ArrayList<Cars>)
        fun showProgress()
        fun hideProgress()
        fun showBtnViews()
        fun hideBtnViews()
    }

    interface PresenterInterface {
        fun launchCarsInformation(list: ArrayList<Cars>)
        fun launchCarsLocation(list: ArrayList<Cars>)
        fun retry()
        fun closeProgress()
    }

}