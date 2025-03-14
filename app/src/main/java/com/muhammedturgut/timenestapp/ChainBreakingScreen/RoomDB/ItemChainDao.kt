package com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB

import androidx.room.*
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain

@Dao
interface ItemChainDao {

    @Query("SELECT * FROM chainItem")
    suspend fun getItemWithNameAndId(): List<ItemChain>

    @Query("SELECT * FROM chainItem WHERE id = :id")
    suspend fun getItemById(id: Int): ItemChain?

    @Insert
    suspend fun insert(item: ItemChain)

    @Delete
    suspend fun delete(item: ItemChain)

    @Update
    suspend fun update(item: ItemChain)
} 