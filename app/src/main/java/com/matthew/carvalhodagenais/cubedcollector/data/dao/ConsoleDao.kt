package com.matthew.carvalhodagenais.cubedcollector.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.cubedcollector.data.entities.Console

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

    @Query("SELECT * FROM console_table ORDER BY title")
    fun getAllConsoles(): LiveData<List<Console>>
}