package com.example.model

data class DestinationEntity(
    val destinations: List<Results>
)
data class Results (
    val city :String,
    val country :String,
    val iata :String,
    val publicName :PublicName
)
data class PublicName (
    val english :String
)