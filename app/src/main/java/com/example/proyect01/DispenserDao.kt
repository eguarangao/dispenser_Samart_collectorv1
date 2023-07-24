package com.example.proyect01

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DispenserDao {
    @Query("Select * from DispenserEntity")
    fun getAllDispenser(): MutableList<DispenserEntity>

    @Insert
    fun addDispenser(dispenserEntity: DispenserEntity)

    @Update
    fun updateDispenser(dispenserEntity: DispenserEntity)
    @Delete
    fun deleteDispenser(dispenserEntity: DispenserEntity)
}