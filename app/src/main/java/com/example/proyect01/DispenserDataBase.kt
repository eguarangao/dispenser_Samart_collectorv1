package com.example.proyect01

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(DispenserEntity::class), version = 2)
abstract class DispenserDataBase: RoomDatabase() {
abstract fun dispenserDao():DispenserDao
}