package com.cup.stickerworldcupcontrol.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cup.stickerworldcupcontrol.database.dao.CellDao
import com.cup.stickerworldcupcontrol.database.models.Cell
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Cell::class], version = 4, exportSchema = false)
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
                    //.addMigrations(MIGRATION_4_5)
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
            for (section in listOfSections) {
                for (sectionStickerNumber in section.fromNumber..section.toNumber) {
                    val numberOfSticker =
                        if (sectionStickerNumber < 10 && section.sectionSimbol != "COC") {
                            "0".plus(sectionStickerNumber)
                        } else {
                            sectionStickerNumber.toString()
                        }

                    if (section.simbol.isEmpty()) {
                        list.add(
                            Cell(
                                id = stickerId,
                                label = numberOfSticker,
                                text = "",
                                isSelected = false,
                                numberRepeated = 0,
                                sectionSimbol = section.sectionSimbol
                            )
                        )
                    } else {
                        list.add(
                            Cell(
                                id = stickerId,
                                label = section.simbol,
                                text = numberOfSticker,
                                isSelected = false,
                                numberRepeated = 0,
                                sectionSimbol = section.sectionSimbol
                            )
                        )
                    }
                    stickerId++
                }
            }

            return list
        }
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("UPDATE cells SET text = '00' WHERE text = '20'")
    }
}