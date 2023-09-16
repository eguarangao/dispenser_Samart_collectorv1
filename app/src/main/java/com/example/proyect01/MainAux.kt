package com.example.proyect01

import com.example.proyect01.entity.DispenserEntity

interface MainAux {
    fun hideFab(isVisible: Boolean = false)
    fun addDispenser(dispenserEntity: DispenserEntity)
    fun updateDispense(dispenserEntity: DispenserEntity)
}