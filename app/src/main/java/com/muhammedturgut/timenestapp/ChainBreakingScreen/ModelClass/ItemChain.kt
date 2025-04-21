package com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "chainItem")
data class ItemChain (

    @ColumnInfo(name = "Chain_Name")
    val chainName: String?,

    @ColumnInfo(name = "Chain_About")
    val chainAbout: String?,

    @ColumnInfo(name = "Chain_State")
    val chainState: Int,

   @ColumnInfo(name = "notId")
    val notId:Int

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class ChainWithDetails(
    @Embedded val chain: ItemChain,  // Ana tablo

    @Relation(
        parentColumn = "notId",      // ItemChain içinde
        entityColumn = "notId"       // ItemDetailChain içinde
    )
    val details: List<ItemDetailChain> // Detayların listesi
)


@Entity(tableName = "chainItemDetails")
data class ItemDetailChain(
    @ColumnInfo(name = "notId")
    val notId:Int,

    @ColumnInfo(name = "notAbout")
    val notAbout:String?,

    @ColumnInfo(name = "notDate")
    val notDate:String?

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
