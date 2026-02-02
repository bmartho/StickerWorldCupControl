package com.cup.stickerworldcupcontrol.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cup.stickerworldcupcontrol.database.dao.models.Cell
import kotlinx.coroutines.flow.Flow

@Dao
interface CellDao {
    @Query("SELECT * FROM cells")
    fun getAllCells(): Flow<List<Cell>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cells: List<Cell>)

    @Update
    suspend fun updateCell(cell: Cell)

    @Query("DELETE FROM cells")
    suspend fun deleteAll()
}