package com.muhammedturgut.timenestapp.ChainBreakingScreen.RoomDB

import androidx.room.TypeConverter
import java.util.BitSet

class Converters {
    @TypeConverter
    fun fromBitSet(bitSet: BitSet): ByteArray {
        return bitSet.toByteArray()
    }

    @TypeConverter
    fun toBitSet(bytes: ByteArray): BitSet {
        return BitSet.valueOf(bytes)
    }
}