package com.example.proyect01

interface MainAux {
    fun hideFab(isVisible: Boolean = false)
    fun addDispenser(dispenserEntity: DispenserEntity)
    fun updateDispense(dispenserEntity: DispenserEntity)
}