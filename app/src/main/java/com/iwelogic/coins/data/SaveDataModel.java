package com.iwelogic.coins.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SaveDataModel")
public class SaveDataModel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "dataKey")
    private String dataKey;

    @ColumnInfo(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    String getDataKey() {
        return dataKey;
    }

    void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }
}