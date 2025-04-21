package com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB.ItemChainDao

@Database(entities = [ItemChain::class, ItemDetailChain::class], version = 1)
abstract class ItemChainDatabase : RoomDatabase() {
    abstract fun itemChainDao(): ItemChainDao
    abstract fun itemDetailDao(): ItemDetailDao
}
