package com.iwelogic.coins.data;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import com.google.gson.Gson;
import com.iwelogic.coins.App;

@Database(entities = {SaveDataModel.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public static DataBase getInstance(){
        return Room.databaseBuilder(App.instance.getApplicationContext(), DataBase.class, "word_database").allowMainThreadQueries().build();
    }

    public abstract SaveDataDao mSaveDataDao();

    public void writeObject(String key, Object object) {
        SaveDataModel saveData = new SaveDataModel();
        saveData.setDataKey(key);
        saveData.setValue(new Gson().toJson(object));
        mSaveDataDao().deleteByKey(key);
        mSaveDataDao().insert(saveData);
    }

    public <T> T readObject(String key, Class<T> type) {
        return new Gson().fromJson(mSaveDataDao().getSaveData(key).getValue(), type);
    }

    @Dao
    public interface SaveDataDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(SaveDataModel saveData);

        @Update()
        void update(SaveDataModel saveData);

        @Query("DELETE FROM SaveDataModel")
        void deleteAll();

        @Query("DELETE FROM SaveDataModel WHERE dataKey = :dataKey")
        void deleteByKey(String dataKey);

        @Query("SELECT * from SaveDataModel where dataKey = :dataKey")
        SaveDataModel getSaveData(String dataKey);
    }
}