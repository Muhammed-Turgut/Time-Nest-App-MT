package com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB

import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemChain
import androidx.room.*
import com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass.ItemDetailChain
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemChainDao {

    @Query("SELECT * FROM chainItem")
    suspend fun getItemWithNameAndId(): List<ItemChain>

    @Query("SELECT * FROM chainItem WHERE notId = :id")
    suspend fun getItemById(id: Int): ItemChain?

    @Query("SELECT * FROM chainItem WHERE notId = :notId")
    suspend fun getItemByNOTId(notId: Int): ItemChain?


    @Insert
    suspend fun insert(item: ItemChain)

    @Delete
    suspend fun delete(item: ItemChain)

    @Update
    suspend fun update(item: ItemChain)
}

@Dao
interface ItemDetailDao {

    @Query("SELECT * FROM chainItemDetails WHERE notOwnerId = :notId")
    suspend fun getDetailsByNotId(notId: Int): List<ItemDetailChain>

    @Insert
    suspend fun insert(item: ItemDetailChain)

    @Delete
    suspend fun delete(item: ItemDetailChain)
}