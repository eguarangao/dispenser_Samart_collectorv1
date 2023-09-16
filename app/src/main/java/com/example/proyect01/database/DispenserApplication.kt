package com.example.proyect01.database

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.proyect01.DispenserDataBase

class DispenserApplication : Application() {
    companion object {
        lateinit var dataBase: DispenserDataBase
    }

    override fun onCreate() {
        super.onCreate()
        val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(dataBase: SupportSQLiteDatabase) {

                dataBase.execSQL("ALTER TABLE DispenserEntity ADD COLUMN direccion TEXT NOT NULL DEFAULT''")
            }
        }

        dataBase = Room.databaseBuilder(
            this, DispenserDataBase::
            class.java, "DispenserDataBase"
        ).addMigrations(MIGRATION_1_2).build()

    }
}