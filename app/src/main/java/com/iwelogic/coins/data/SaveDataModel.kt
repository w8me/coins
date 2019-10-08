package com.iwelogic.coins.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SaveDataModel")
class SaveDataModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "dataKey")
    var dataKey: String = "key"

    @ColumnInfo(name = "value")
    var value: String = "value"
}