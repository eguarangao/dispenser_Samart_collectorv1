package com.example.proyect01

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DispenserEntity")
data class DispenserEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var referencia: String,
    var direccion: String
) {
}
