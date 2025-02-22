package com.muhammedturgut.timenestapp.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.muhammedturgut.timenestapp.ModelClass.Item

@Database(entities = [Item::class], version = 2)
abstract class ItemGoalDatabase : RoomDatabase() {
    abstract fun itemGoalDao(): ItemGoalDao

    companion object {
        public val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration işlemleri buraya yazılır
            }
        }
    }
}