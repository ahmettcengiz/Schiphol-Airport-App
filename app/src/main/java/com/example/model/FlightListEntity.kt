package com.example.model

data class FlightListEntity (
    val flights: List<FlightModel>
)
data class FlightModel(
    val id: String,
    val flightName : String,
    val flightDirection : String,
    val flightNumber : Int,
    val route: RouteType,
    val scheduleDateTime  : String,
    val prefixIATA: String
)
data class RouteType (
    val destinations : List<String>

)