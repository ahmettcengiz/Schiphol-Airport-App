package com.example.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Flight")
data class ResEntity (
    @PrimaryKey @ColumnInfo(name = "ID") val id: String,
    @ColumnInfo(name = "seat") val seat : String,
    @ColumnInfo(name = "iata")val iata : String,
    @ColumnInfo(name = "flightName")val flightName : String,
    @ColumnInfo(name = "flightDirection")val flightDirection : String,
    @ColumnInfo(name = "scheduleDateTime")val scheduleDateTime  : String,


)

