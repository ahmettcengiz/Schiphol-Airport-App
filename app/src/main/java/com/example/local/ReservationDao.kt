package com.example.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.model.ResEntity

@Dao
interface ReservationDao {
    @Query("SELECT * FROM Flight")
    fun findAll(): LiveData<List<ResEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(eesEntity: ResEntity)
}