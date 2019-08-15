package com.example.wunder.main

import android.util.Log
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainInteractor {

    private val TAG = this::class.java.simpleName

    private lateinit var arrayList: ArrayList<Cars>

    fun comparison(activityName: String, listener: MainContract.PresenterInterface) {

        // tell presenter to tell the view which activity should be launched
        // send as input the cars information we fetch previously

        if (activityName == "CarsInformation"){ listener.launchCarsInformation(arrayList) }

        else if (activityName == "CarsLocation"){ listener.launchCarsLocation(arrayList) }

    }

    fun fetchList(listener: MainContract.PresenterInterface){

        // fetch the JSON file from the internet
        // transform it to an ArrayList filled with <Cars> objects

        val url = "https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json"

        doAsync {

            val connection = URL(url).openConnection() as HttpURLConnection
            val fetchJson: String

            try {
                connection.connect()
                fetchJson = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } catch (e: Exception) { // internet not working
                Log.d(TAG,"ALERT: Internet connection not working")
                listener.retry()
                return@doAsync
            }
            finally {
                connection.disconnect()
            }

            uiThread {

                val jsonArray = JSONArray(JSONObject(fetchJson).getString("placemarks"))
                arrayList =  handleJSON(jsonArray) // transform JSON
                listener.closeProgress() // tell presenter to tell view that it can close the progress bar and show the button views
            }
        }
    }

    fun handleJSON(jsonArray: JSONArray): ArrayList<Cars>{

        val listCarInfo = ArrayList<Cars>()

        var index = 0

        while (index < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)

            val name = jsonObject.getString("name")
            val adress = jsonObject.getString("address")
            val coordinates = jsonObject.getJSONArray("coordinates")

            val lng = coordinates.getString(0).toDouble()
            val lat = coordinates.getString(1).toDouble()

            listCarInfo.add(Cars(name,adress,lat,lng))
            index++
        }
        return listCarInfo
    }
}