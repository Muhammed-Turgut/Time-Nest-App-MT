package com.muhammedturgut.timenestapp.ChainBreakingScreen.ModelClass


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "chainItem")
data class ItemChain(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notId")
    val notId: Int = 0,  // Primary Key

    @ColumnInfo(name = "Chain_Name")
    val chainName: String?,

    @ColumnInfo(name = "Chain_About")
    val chainAbout: String?,

    @ColumnInfo(name = "Chain_State")
    val chainState: Int
)


@Entity(
    tableName = "chainItemDetails",
    foreignKeys = [
        ForeignKey(
            entity = ItemChain::class,
            parentColumns = ["notId"],
            childColumns = ["notOwnerId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    indices = [Index(value = ["notOwnerId"])]
)
data class ItemDetailChain(

    @PrimaryKey(autoGenerate = true)
    val detailId: Int = 0,

    @ColumnInfo(name = "notOwnerId")  // Foreign key
    val notOwnerId: Int,

    @ColumnInfo(name = "notsAbout")
    val notAbout: String?,

    @ColumnInfo(name = "notDate")
    val notDate: String?
)

data class ItemWithDetails(
    @Embedded val item: ItemChain,

    @Relation(
        parentColumn = "notId",
        entityColumn = "notOwnerId"
    )
    val details: List<ItemDetailChain>
)
