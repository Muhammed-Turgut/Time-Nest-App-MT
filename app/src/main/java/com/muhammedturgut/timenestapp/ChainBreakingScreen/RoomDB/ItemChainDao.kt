package com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB

import androidx.room.*
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ChainWithDetails
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain

@Dao
interface ItemChainDao {

    @Query("SELECT * FROM chainItem")
    suspend fun getItemWithNameAndId(): List<ItemChain>

    @Query("SELECT * FROM chainItem WHERE id = :id")
    suspend fun getItemById(id: Int): ItemChain?

    @Query("SELECT * FROM chainItem WHERE notId = :notId")
    suspend fun getItemBynOTId(notId: Int): ItemChain?

    @Transaction
    @Query("SELECT * FROM chainItem WHERE notId = :notId")
    suspend fun getChainWithDetails(notId: Int): List<ChainWithDetails>

    @Insert
    suspend fun insert(item: ItemChain)

    @Delete
    suspend fun delete(item: ItemChain)

    @Update
    suspend fun update(item: ItemChain)
}

@Dao
interface ItemDetailDao {

    @Query("SELECT * FROM chainItemDetails WHERE notId = :notId")
    suspend fun getDetailsByNotId(notId: Int): List<ItemDetailChain>

    @Insert
    suspend fun insert(item: ItemDetailChain)
}