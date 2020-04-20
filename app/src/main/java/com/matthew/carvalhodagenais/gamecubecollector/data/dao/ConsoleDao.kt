package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console

@Dao
interface ConsoleDao {

    @Insert
    suspend fun insert(console: Console)

    @Update
    suspend fun update(console: Console)

    @Delete
    suspend fun delete(console: Console)

    @Query("DELETE FROM console_table")
    suspend fun deleteAllConsoles()

    @Query("SELECT * FROM console_table")
    fun getAllConsoles(): LiveData<List<Console>>
}