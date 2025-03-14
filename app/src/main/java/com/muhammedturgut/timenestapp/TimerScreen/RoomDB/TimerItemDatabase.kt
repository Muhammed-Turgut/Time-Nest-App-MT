package com.muhammedturgut.timenestapp.TimerScreen.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem

@Database(entities = [TimerItem::class], version = 1)
abstract class TimerItemDatabase: RoomDatabase() {
    abstract fun TimerItemDao(): timerItemDao
}