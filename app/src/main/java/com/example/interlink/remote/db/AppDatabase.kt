package com.example.interlink.remote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interlink.model.Alert
import android.content.Context
import androidx.room.Room

@Database(entities = [Alert::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao
}

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "alert_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}