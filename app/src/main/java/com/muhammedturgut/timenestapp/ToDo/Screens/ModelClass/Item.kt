package com.muhammedturgut.timenestapp.ToDo.Screens.ModelClass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @ColumnInfo(name = "mission_Name")
    var missionName: String?,

    @ColumnInfo(name = "mission_about")
    var missionAbout: String?,

    @ColumnInfo(name = "start_Time")
    var startTime: String?,

    @ColumnInfo(name = "end_Time")
    var endTime: String?,

    @ColumnInfo(name = "State")
    var State: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}