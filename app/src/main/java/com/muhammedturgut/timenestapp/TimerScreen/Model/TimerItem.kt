package com.muhammedturgut.timenestapp.TimerScreen.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer_item")
data class TimerItem(
    @ColumnInfo(name = "timer_name")
    val timerName: String?,

    @ColumnInfo(name = "timer_hour")
    val timer_hour: Int,

    @ColumnInfo(name = "timer_minute")
    val timer_minute: Int,

    @ColumnInfo(name = "timer_second")
    val timer_second: Int,

    @ColumnInfo(name = "timer_order")
    val timerOrder: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun toLong(): Long {
        return ((timer_hour * 3600L) + (timer_minute * 60L) + timer_second) * 1000L
    }
}