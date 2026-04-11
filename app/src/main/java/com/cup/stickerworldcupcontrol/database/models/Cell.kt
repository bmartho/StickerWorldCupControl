package com.cup.stickerworldcupcontrol.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cells")
data class Cell(
    @PrimaryKey
    val id: Int,

    val label: String,
    val number: Int,
    val numberRepeated: Int,
    val isSelected: Boolean,
    val sectionSimbol: String
)