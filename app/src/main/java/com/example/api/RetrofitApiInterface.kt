package com.example.api

import com.example.model.FlightListEntity
import com.example.model.FlightModel
import com.example.model.Results
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiInterface {

    @Headers(
        "Accept: application/json",
        "ResourceVersion: v4",
        "app_id: cda97296",
        "app_key: 815684e8a78dc64779bbc38b0451c5ba"
    )
    @GET("flights")
    fun getFlightList(
        @Query("page") page: Int
    ): Observable<FlightListEntity>

    @Headers(
        "Accept: application/json",
        "ResourceVersion: v4",
        "app_id: cda97296",
        "app_key: 815684e8a78dc64779bbc38b0451c5ba"
    )
    @GET("flights/{id}")
    fun getFlightListbyID(
        @Path("id") id: String
    ): Observable<FlightModel>

    @Headers(
        "Accept: application/json",
        "ResourceVersion: v4",
        "app_id: cda97296",
        "app_key: 815684e8a78dc64779bbc38b0451c5ba"
    )
    @GET("destinations/{iata}")
    fun getDestinationID(
        @Path("iata") iata: String
    ): Observable<Results>

}
