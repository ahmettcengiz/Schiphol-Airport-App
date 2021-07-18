package com.example.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.api.ApiClient
import com.example.api.RetrofitApiInterface
import com.example.model.DestinationEntity
import com.example.model.FlightListEntity
import com.example.model.FlightModel
import com.example.model.Results
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FlightListViewModel : ViewModel() {
    private lateinit var postService: RetrofitApiInterface

    var listOfFlight = MutableLiveData<FlightListEntity?>()
    var destination = MutableLiveData<Results?>()
    var flightByID = MutableLiveData<FlightModel?>()
    var errorLD = MutableLiveData<Throwable?>()

    @SuppressLint("CheckResult")
    fun getListOfFlights(page : Int) {
        postService = ApiClient.getClient().create(RetrofitApiInterface::class.java)
        postService.getFlightList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rr ->
                rr?.let {
                    listOfFlight.value = rr
                    errorLD.value = null
                } ?: run {
                    listOfFlight.value = null
                    errorLD.value = null
                }
            }, { error ->
                errorLD.value = error
                listOfFlight.value = null
            })
    }
    @SuppressLint("CheckResult")
    fun getFlightByID(id:String) {
        postService = ApiClient.getClient().create(RetrofitApiInterface::class.java)
        postService.getFlightListbyID(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rr ->
                rr?.let {
                    flightByID.value = rr
                    errorLD.value = null
                } ?: run {
                    flightByID.value = null
                    errorLD.value = null
                }
            }, { error ->
                errorLD.value = error
                flightByID.value = null
            })
    }
    @SuppressLint("CheckResult")
    fun getDestination(iata : String) {
        postService = ApiClient.getClient().create(RetrofitApiInterface::class.java)
        postService.getDestinationID(iata)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rr ->
                rr?.let {
                    destination.value = rr
                    errorLD.value = null
                } ?: run {
                    destination.value = null
                    errorLD.value = null
                }
            }, { error ->
                errorLD.value = error
                destination.value = null
            })
    }

}
