package com.example.proyect01.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WasteEntity")
data class WasteEntity ( @PrimaryKey val id: Long,
                         val dispenserId: Long,
                         val trashState: String,
                         val capacity: Int)