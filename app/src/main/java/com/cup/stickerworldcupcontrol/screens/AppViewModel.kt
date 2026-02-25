package com.cup.stickerworldcupcontrol.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cup.stickerworldcupcontrol.database.dao.CellDao
import com.cup.stickerworldcupcontrol.database.dao.models.Cell
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppViewModel(private val dao: CellDao) : ViewModel() {
    val listCells: Flow<List<Cell>> = dao.getAllCells()

    fun onCellClick(cell: Cell) {
        viewModelScope.launch {
            dao.updateCell(cell.copy(isSelected = !cell.isSelected))
        }
    }

    fun onIncreaseRepeatedClick(cell: Cell) {
        if (cell.numberRepeated >= 9) {
            return
        }

        viewModelScope.launch {
            dao.updateCell(cell.copy(numberRepeated = cell.numberRepeated + 1))
        }
    }

    fun onDecreaseRepeatedClick(cell: Cell) {
        if (cell.numberRepeated <= 0) {
            return
        }

        viewModelScope.launch {
            dao.updateCell(cell.copy(numberRepeated = cell.numberRepeated - 1))
        }
    }

    fun cleanAlbum() {
        viewModelScope.launch {
            dao.cleanAll()
        }
    }

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