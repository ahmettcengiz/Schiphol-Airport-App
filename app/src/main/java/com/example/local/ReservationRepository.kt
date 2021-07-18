package com.example.local

import androidx.lifecycle.LiveData
import com.example.model.ResEntity


class ReservationRepository(private val reservationDao: ReservationDao) {

    val allReservation: LiveData<List<ResEntity>> = reservationDao.findAll()

    fun insertReservation(resEntity: ResEntity) {
        reservationDao.insert(resEntity)
    }

}