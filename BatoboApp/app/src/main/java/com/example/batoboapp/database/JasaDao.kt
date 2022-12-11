package com.example.batoboapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface JasaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(jasa: Jasa)

    @Update
    fun update(jasa: Jasa)

    @Delete
    fun delete(jasa: Jasa)

    @Query("SELECT * from jasa ORDER BY id ASC")
    fun getAllJasa(): LiveData<List<Jasa>>
}