package com.cup.stickerworldcupcontrol.database.dao.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cells")
data class Cell(
    @PrimaryKey
    val id: Int,

    val text: String,
    val numberRepeated: Int,
    val isSelected: Boolean
)