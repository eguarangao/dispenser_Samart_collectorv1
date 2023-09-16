package com.example.proyect01.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DispenserEntity")
data class DispenserEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var referencia: String = "",
    var direccion: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DispenserEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
