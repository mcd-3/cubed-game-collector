package com.matthew.carvalhodagenais.gamecubecollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.Console

interface ConsoleDao {

    @Insert
    suspend fun insert(console: Console)

    @Update
    suspend fun update(console: Console)

    @Delete
    suspend fun delete(console: Console)

    @Query("SELECT * FROM console_table")
    fun getAllConsoles(): LiveData<List<Console>>
}