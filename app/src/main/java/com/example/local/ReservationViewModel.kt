package com.example.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.model.ResEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReservationViewModel(application: Application) : AndroidViewModel(application) {

    val allReservation: LiveData<List<ResEntity>>

    private val repository: ReservationRepository

    init {
        val reservationDao = LocalDatabase.getAppDatabase(application)?.reservationDao()
        repository = ReservationRepository(reservationDao!!)
        allReservation = repository.allReservation
    }

    fun insertReservation(resEntity: ResEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertReservation(resEntity)
        }
    }



}