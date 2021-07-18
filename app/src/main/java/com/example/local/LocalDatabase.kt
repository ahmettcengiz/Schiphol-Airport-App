package com.example.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.model.ResEntity

const val DB_VERSION = 1

@Database(entities = [ResEntity::class], version = DB_VERSION, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun reservationDao(): ReservationDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getAppDatabase(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<LocalDatabase>(
                    context.applicationContext, LocalDatabase::class.java, "ReservationDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}