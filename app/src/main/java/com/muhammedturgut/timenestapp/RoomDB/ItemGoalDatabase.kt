package com.muhammedturgut.timenestapp.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedturgut.timenestapp.ModelClass.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemGoalDatabase : RoomDatabase() {
    abstract fun itemGoalDao(): ItemGoalDao
}