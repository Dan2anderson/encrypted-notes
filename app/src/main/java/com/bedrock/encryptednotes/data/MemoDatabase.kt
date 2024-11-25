package com.bedrock.encryptednotes.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log

@Database(entities = [MemoModel::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    companion object {
        @Volatile
        private var INSTANCE: MemoDatabase? = null

        fun getDatabase(context: Context): MemoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = try {
                    Room.databaseBuilder(
                        context.applicationContext,
                        MemoDatabase::class.java,
                        "memo_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                } catch (e: Exception) {
                    Log.e("MemoDatabase", "Error creating database: ${e.message}")
                    throw e
                }
                INSTANCE = instance
                instance
            }
        }
    }
}