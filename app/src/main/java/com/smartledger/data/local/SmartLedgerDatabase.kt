package com.smartledger.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.smartledger.data.local.dao.ExpenseDao
import com.smartledger.data.local.entity.ExpenseEntity

/*
  Main Room database for Smart Ledger.

  This class registers the local database entities and exposes DAOs that the
  repository layer can use for local persistence.
*/
@Database(
    entities = [ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SmartLedgerDatabase : RoomDatabase() {

    /*
      Provides access to local expense database operations.
    */
    abstract fun expenseDao(): ExpenseDao

    companion object {
        /*
          Volatile ensures that all threads see the same database instance.

          This is important because the app should not create multiple Room
          database instances during navigation or ViewModel recreation.
        */
        @Volatile
        private var INSTANCE: SmartLedgerDatabase? = null

        /*
          Creates the Room database as a singleton.

          The application context is used to avoid leaking an Activity context.
        */
        fun getInstance(context: Context): SmartLedgerDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SmartLedgerDatabase::class.java,
                    "smart_ledger_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}