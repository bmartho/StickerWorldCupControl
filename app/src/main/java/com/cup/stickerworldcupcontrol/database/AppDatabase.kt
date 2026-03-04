package com.cup.stickerworldcupcontrol.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cup.stickerworldcupcontrol.database.dao.CellDao
import com.cup.stickerworldcupcontrol.database.dao.models.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cell::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cellDao(): CellDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration(true)
                    //.addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    val dao = database.cellDao()
                    val initialCells = buildCells()
                    dao.insertAll(initialCells)
                }
            }
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)

            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.cellDao())
                }
            }
        }

        suspend fun populateDatabase(cellDao: CellDao) {
            // Delete all content here.
            cellDao.deleteAll()

            val initialCells = buildCells()
            cellDao.insertAll(initialCells)
        }

        private fun buildCells(): List<Cell> {
            val list = mutableListOf<Cell>()
            var stickerId = 1
            var sessionNumber = 1
            for (session in listOfSessions) {
                if (sessionNumber == 1) {
                    list.add(
                        Cell(
                            id = stickerId,
                            label = "00",
                            text = "",
                            isSelected = false,
                            numberRepeated = 0
                        )
                    )
                    stickerId++
                } else {
                    for (sessionStickerNumber in 1..session.second) {
                        val numberOfSticker = if (sessionNumber == listOfSessions.size - 1) {
                            (18 + sessionStickerNumber).toString()
                        } else if (sessionStickerNumber < 10) {
                            "0".plus(sessionStickerNumber)
                        } else {
                            sessionStickerNumber.toString()
                        }

                        list.add(
                            Cell(
                                id = stickerId,
                                label = session.first,
                                text = numberOfSticker,
                                isSelected = false,
                                numberRepeated = 0
                            )
                        )
                        stickerId++
                    }
                }
                sessionNumber++
            }
            return list
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        //nothing
    }
}