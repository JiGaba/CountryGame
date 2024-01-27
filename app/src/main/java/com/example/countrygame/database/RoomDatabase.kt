package com.example.countrygame.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDataDao {
    @Query("SELECT * FROM country_data")
    suspend fun getAllCountryData(): List<CountryDataDTO>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryData: CountryDataDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryDataList: List<CountryDataDTO>)
}

@Database(entities = [CountryDataDTO::class], version = 1)
abstract class MyRoomDatabase: RoomDatabase() {
    abstract val countryDataDao: CountryDataDao
}

private lateinit var INSTANCE: MyRoomDatabase

fun getDatabase(context: Context): MyRoomDatabase {
    synchronized(MyRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MyRoomDatabase::class.java,
                "my_room_database").build()
        }
    }
    return INSTANCE
}