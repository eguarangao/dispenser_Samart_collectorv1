package com.example.proyect01

import com.example.proyect01.entity.DispenserEntity

interface OnClickListener {
    fun onCLick(dispenserId: Long)
    fun onDeleteDispenser(dispenserEntity: DispenserEntity)
}