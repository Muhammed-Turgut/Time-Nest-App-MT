package com.muhammedturgut.timenestapp.RoomDB

import androidx.room.*
import com.muhammedturgut.timenestapp.ModelClass.Item

@Dao
interface ItemGoalDao {
    @Query("SELECT * FROM item")
    suspend fun getItemWithNameAndId(): List<Item>

    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun getItemById(id: Int): Item?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun Update(item: Item)
} 