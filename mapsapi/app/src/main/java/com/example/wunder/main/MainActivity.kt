package com.example.wunder.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.wunder.R
import com.example.wunder.carsInformation.CarsInformationActivity
import com.example.wunder.carsLocation.CarsLocationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.ViewInterface {

    private val TAG = this::class.java.simpleName

    private val presenter = MainPresenter(this, MainInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.start() // fetch data

        // set buttons reaction to click
        carsInformation.setOnClickListener { presenter.launchActivity("CarsInformation") }
        carsLocation.setOnClickListener { presenter.launchActivity("CarsLocation") }

    }

    override fun onDestroy() { // avoid leaking the activity with long-running tasks
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun showProgress() { // show progress bar
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() { // hide progress bar
        progressBar.visibility = View.GONE
    }

    override fun showBtnViews() { // show bts views
        carsInformation.visibility = View.VISIBLE
        carsLocation.visibility = View.VISIBLE
    }

    override fun hideBtnViews() { // hide bts views
        carsInformation.visibility = View.GONE
        carsLocation.visibility = View.GONE
    }

    override fun navigateToCarsInformation(list: ArrayList<Cars>) { // launch carsInformationActivity

        val intent = Intent(this, CarsInformationActivity::class.java).apply {
            putParcelableArrayListExtra("data",list as java.util.ArrayList<out Parcelable>) // to send data about all the cars
        }
        startActivity(intent)
    }

    override fun navigateToCarsLocation(list: ArrayList<Cars>){ // launch carsLocationActivity

        val intent = Intent(this, CarsLocationActivity::class.java).apply {
            putParcelableArrayListExtra("data",list as java.util.ArrayList<out Parcelable>) // to send data about all the cars
            putExtra("type","all") // to distinguish where the intent came from
        }
        startActivity(intent)
    }
}
