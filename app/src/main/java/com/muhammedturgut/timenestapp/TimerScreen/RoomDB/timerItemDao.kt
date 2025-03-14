package com.muhammedturgut.timenestapp.TimerScreen.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muhammedturgut.timenestapp.TimerScreen.Model.TimerItem


@Dao
interface timerItemDao {

    @Query("SELECT * FROM timer_item ORDER BY id ASC")
    suspend fun getItemWithNameAndId(): List<TimerItem>

    @Query("SELECT * FROM timer_item WHERE id = :id")
    fun getItemById(id: Int): TimerItem?

    @Insert
    suspend fun insert(item: TimerItem) //Ekelem

    @Delete
    suspend fun delete(item: TimerItem) //Silme

    @Update
    suspend fun update(item: TimerItem) // Güncelme işlemleri için
}