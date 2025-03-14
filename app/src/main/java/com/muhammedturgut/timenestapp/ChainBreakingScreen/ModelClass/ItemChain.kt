package com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chainItem")

data class ItemChain (

    @ColumnInfo(name = "Chain_Name")
    val chainName: String?,

    @ColumnInfo(name = "Chain_About")
    val chainAbout: String?,

    @ColumnInfo(name = "Chain_State")
    val chainState: Int,

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
