package com.example.interlink.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StoredEvent::class, FavoriteDevice::class], version = 2, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase(){
    abstract fun favoriteDeviceDao() : FavoriteDeviceDao
    abstract fun storedEventDao() : StoredEventDao

    companion object{
        @Volatile
        private var Instance: FavoritesDatabase? = null

        fun getDatabase(context: Context): FavoritesDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    FavoritesDatabase::class.java,
                    "interlink_database"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }
        }
    }
}