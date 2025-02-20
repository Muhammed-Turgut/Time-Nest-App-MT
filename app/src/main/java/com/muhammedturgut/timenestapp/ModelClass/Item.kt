package com.muhammedturgut.timenestapp.ModelClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @ColumnInfo(name = "mission_Name")
    val missionName: String?,

    @ColumnInfo(name = "start_Time")
    val startTime: String?,

    @ColumnInfo(name = "end_Time")
    val endTime: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}