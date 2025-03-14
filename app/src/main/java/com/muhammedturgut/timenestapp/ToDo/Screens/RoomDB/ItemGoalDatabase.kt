package com.muhammedturgut.timenestapp.ToDo.Screens.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemGoalDatabase : RoomDatabase() {
    abstract fun itemGoalDao(): ItemGoalDao
}