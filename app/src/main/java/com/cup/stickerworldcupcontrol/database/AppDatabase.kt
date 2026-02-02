package com.cup.stickerworldcupcontrol.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cup.stickerworldcupcontrol.database.dao.CellDao
import com.cup.stickerworldcupcontrol.database.dao.models.Cell

@Database(entities = [Cell::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cellDao(): CellDao
}