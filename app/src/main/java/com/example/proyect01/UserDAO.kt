package com.example.proyect01

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.proyect01.entity.DispenserEntity
import com.example.proyect01.entity.UserEntity
import com.example.proyect01.entity.UserWithDispenser

@Dao
interface UserDAO {
    @Insert
    fun addUser(dispenserEntity: UserEntity): Long
    @Query("Select * from UserEntity where id=:id")
    fun getUserById(id: Long): UserEntity


}