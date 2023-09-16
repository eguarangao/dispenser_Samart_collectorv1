package com.example.proyect01.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombres: String,
    val apellidos: String,
    val password:String,
    val username:String
)
