package com.example.wunder.main

import android.os.Parcel
import android.os.Parcelable

// implement a class to support the cars information
// parcelable implementation was done so we could pass ArrayList<Cars> through intents

data class Cars(val name: String, val adress: String, val lat: Double, val lng: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(adress)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Cars> {
            override fun createFromParcel(parcel: Parcel): Cars {
                return Cars(parcel)
            }

            override fun newArray(size: Int): Array<Cars?> {
                return arrayOfNulls(size)
            }
        }
    }
}
