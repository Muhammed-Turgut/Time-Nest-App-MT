package com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain

@Database(entities = [ItemChain::class], version = 1)
abstract class ItemChainDatabase : RoomDatabase() {
    abstract fun itemChainDao(): ItemChainDao
}