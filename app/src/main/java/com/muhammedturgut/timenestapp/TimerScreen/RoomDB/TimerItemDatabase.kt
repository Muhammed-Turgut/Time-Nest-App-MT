package com.muhammedturgut.timenestapp.TimerScreen.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem

@Database(entities = [TimerItem::class], version = 1)
abstract class TimerItemDatabase: RoomDatabase() {
    abstract fun TimerItemDao(): timerItemDao

    companion object {
        public val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration işlemleri buraya yazılır
            }
        }
    }
}