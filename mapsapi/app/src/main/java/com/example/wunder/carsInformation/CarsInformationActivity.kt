package com.example.wunder.carsInformation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.wunder.R
import com.example.wunder.carsLocation.CarsLocationActivity
import com.example.wunder.main.Cars
import kotlinx.android.synthetic.main.activity_cars_information.*

class CarsInformationActivity : AppCompatActivity(),  CarsInformationContract.ViewInterface {

    private val TAG = this::class.java.simpleName

    private val presenter = CarsInformationPresenter(this, CarsInformationInteractor())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cars_information)

        presenter.start(intent) // fetch cars information we received via intent and diplay it

    }

    override fun onDestroy() { // avoid leaking the activity with long-running tasks
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun init() { // set up toolbar properties
        setSupportActionBar(my_toolbar)
        supportActionBar?.title="Cars Available"
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

    override fun navigateToCarLocation(list: ArrayList<Cars>){ // launch carsLocationActivity

        val intent = Intent(this, CarsLocationActivity::class.java).apply {
            putParcelableArrayListExtra("data",list as java.util.ArrayList<out Parcelable>) // to send data about the car that was clicked
            putExtra("type","specific") // to distinguish where the intent came from
        }
        startActivity(intent)

    }

    override fun launchInformation(list: ArrayList<Cars>) { // display the cars information

        listView.adapter = ListViewAdapter(list) // use adapter class to set custom listview
        listView.onItemClickListener = AdapterView.OnItemClickListener { // enable item clicking
            adapterView, view, position, id ->
            presenter.onClickItem(list[position])
        }
    }

    inner class ListViewAdapter(listCars: ArrayList<Cars>) : BaseAdapter() { // custom listview adapter

        private var list = listCars

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.adapter_listview, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.carName.text = list[position].name
            vh.carAdress.text = list[position].adress

            return view
        }

        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }
    }

    private class ViewHolder(view: View?) {
        val carName: TextView = view?.findViewById(R.id.name) as TextView
        val carAdress: TextView = view?.findViewById(R.id.adress) as TextView
    }

}
