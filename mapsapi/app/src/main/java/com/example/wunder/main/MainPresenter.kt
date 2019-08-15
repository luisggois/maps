package com.example.wunder.main

class MainPresenter(var mainView: MainContract.ViewInterface?, val mainInteractor: MainInteractor) :
    MainContract.PresenterInterface {

    private val TAG = this::class.java.simpleName

    fun start() {  // tell view to run progress bar while we fetch the JSON file
        mainView?.hideBtnViews()
        mainView?.showProgress()
        mainInteractor.fetchList(this)
    }

    fun launchActivity(activityName: String) { // check which activity should we lunch, and get input to send via intent
        mainInteractor.comparison(activityName,this)
    }

    fun onDestroy() {
        mainView = null
    }

    override fun retry() { // keep retrying to fetch JSON file if internet not working
        mainInteractor.fetchList(this)
    }

    override fun closeProgress() {  // tell view to disable progress bar and show button views
        mainView?.hideProgress()
        mainView?.showBtnViews()
    }

    override fun launchCarsInformation(list: ArrayList<Cars>) { // tell view to launch carsInformationActivity
        mainView?.apply {
            navigateToCarsInformation(list)
        }
    }

    override fun launchCarsLocation(list: ArrayList<Cars>) { // tell view to launch carsLocationActivity
        mainView?.apply {
            navigateToCarsLocation(list)
        }
    }
}