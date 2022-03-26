package dev.kulloveth.countriesandlanguages.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cals: List<Cal>)

    @Query("SELECT * FROM countries_and_locations")
    fun getALl(): PagingSource<Int, Cal>

    @Query("SELECT * FROM countries_and_locations")
    fun getCal(): Flow<List<Cal>>
}