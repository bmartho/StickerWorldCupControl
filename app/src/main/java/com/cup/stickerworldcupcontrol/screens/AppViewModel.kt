package com.cup.stickerworldcupcontrol.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cup.stickerworldcupcontrol.database.dao.CellDao
import com.cup.stickerworldcupcontrol.database.dao.models.Cell
import kotlinx.coroutines.flow.Flow

class AppViewModel(private val dao: CellDao) : ViewModel() {
    val listCells: Flow<List<Cell>> = dao.getAllCells()

    class Factory(private val cellDao: CellDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(cellDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}