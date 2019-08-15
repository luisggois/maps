package com.example.wunder.main

import org.json.JSONArray
import org.junit.Assert.*
import org.junit.Test

class MainInteractorTest {

    @Test
    fun handleJSONTest(){ // test cars object implementation

        val mainInteractor = MainInteractor()

        val expected = ArrayList<Cars>()
        val output: ArrayList<Cars>

        val jsonTest = JSONArray("""[{"name":"Luis Gois", "adress":Coimbra, "coordinates":[0.0,0.0,0.0]}]""")

        output = mainInteractor.handleJSON(jsonTest)

        assertEquals(expected,output)
    }

}