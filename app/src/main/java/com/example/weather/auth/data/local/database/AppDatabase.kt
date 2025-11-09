package com.example.weather.auth.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.auth.data.local.dao.UserDao
import com.example.weather.auth.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

