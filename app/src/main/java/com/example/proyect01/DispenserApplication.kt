package com.example.proyect01

import android.app.Application
import androidx.room.Room

class DispenserApplication : Application() {
    companion object {
        lateinit var dataBase: DispenserDataBase
    }

    override fun onCreate() {
        super.onCreate()
        dataBase=Room.databaseBuilder(this,DispenserDataBase::class.java,"DispenserDataBase").build()
    }
}