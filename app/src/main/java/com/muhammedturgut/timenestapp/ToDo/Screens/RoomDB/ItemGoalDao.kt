package com.muhammedturgut.timenestapp.ToDo.Screens.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass.Item

@Dao
interface ItemGoalDao {

    @Query("SELECT * FROM item WHERE state != 0")
    suspend fun getItemWithNameAndId(): List<Item>

    @Query("SELECT * FROM item WHERE state != 1")
    fun AimScreenData(): List<Item>

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItemById(id: Int): Item?

    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun update(item: Item)
}

