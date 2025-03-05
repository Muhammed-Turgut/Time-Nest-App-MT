package com.muhammedturgut.timenestapp.TimerScreen.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer item")
data class TimerItem
    (
    @ColumnInfo(name = "timer_name")
    val timerName: String?,

    @ColumnInfo(name = "timer_Amount")
    val timerAmount: String?,

    @ColumnInfo(name = "timer_order")
    val timerOrder: String?,

    )
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

