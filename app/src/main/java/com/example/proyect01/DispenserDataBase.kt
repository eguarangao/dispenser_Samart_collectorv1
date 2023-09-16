package com.example.proyect01

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyect01.entity.DispenserEntity
import com.example.proyect01.entity.UserDispenser
import com.example.proyect01.entity.UserEntity

@Database(entities = arrayOf(DispenserEntity::class), version = 2)
abstract class DispenserDataBase: RoomDatabase() {
abstract fun dispenserDao():DispenserDao
}