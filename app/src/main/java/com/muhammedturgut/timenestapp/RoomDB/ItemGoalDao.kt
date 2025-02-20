package com.muhammedturgut.timenestapp.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.muhammedturgut.timenestapp.ModelClass.Item

@Dao
interface ItemGoalDao {

    @Query("SELECT mission_Name, id, start_Time, end_Time FROM item")
    fun getItemWithNameAndId(): List<Item>

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItemById(id: Int): Item?

    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)
}