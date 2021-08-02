package com.example.bootcamp.ui.map

class Point (id_ : Int, name_ : String, latitude_ : Double, longitude_ : Double) {
    private var id: Int = 0
    private var name: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    init {
        id = id_
        name = name_
        latitude = latitude_
        longitude = longitude_
    }

}