package com.iwelogic.coins.data

import androidx.room.*
import com.google.gson.Gson
import com.iwelogic.coins.App

@Database(entities = [SaveDataModel::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    companion object {
        fun getInstance(): DataBase {
            return Room.databaseBuilder<DataBase>(App.instance.applicationContext, DataBase::class.java, "word_database").allowMainThreadQueries().build()
        }
    }

    abstract fun mSaveDataDao(): SaveDataDao

    fun writeObject(key: String, value : Any) {
        val saveData = SaveDataModel()
        saveData.dataKey = key
        saveData.value = Gson().toJson(value)
        mSaveDataDao().deleteByKey(key)
        mSaveDataDao().insert(saveData)
    }

    fun <T> readObject(key: String, type: Class<T>): T {
        return Gson().fromJson(mSaveDataDao().getSaveData(key).value, type)
    }

    @Dao
    interface SaveDataDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(saveData: SaveDataModel)

        @Update
        fun update(saveData: SaveDataModel)

        @Query("DELETE FROM SaveDataModel")
        fun deleteAll()

        @Query("DELETE FROM SaveDataModel WHERE dataKey = :dataKey")
        fun deleteByKey(dataKey: String)

        @Query("SELECT * from SaveDataModel where dataKey = :dataKey")
        fun getSaveData(dataKey: String): SaveDataModel
    }
}