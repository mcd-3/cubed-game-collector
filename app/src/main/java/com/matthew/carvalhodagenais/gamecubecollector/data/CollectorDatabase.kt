package com.matthew.carvalhodagenais.gamecubecollector.data

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.matthew.carvalhodagenais.gamecubecollector.data.dao.*
import com.matthew.carvalhodagenais.gamecubecollector.data.entities.*
import kotlinx.coroutines.*

@Database(entities = [Game::class, Console::class, Region::class, Type::class, Condition::class, Accessory::class], version = 3)
abstract class CollectorDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun regionDao(): RegionDao
    abstract fun typeDao(): TypeDao
    abstract fun conditionDao(): ConditionDao
    abstract fun consoleDao(): ConsoleDao
    abstract fun accessoryDao(): AccessoryDao

    companion object {

        private var instance: CollectorDatabase? = null

        fun getInstance(context: Context): CollectorDatabase? {
            if (instance == null) {
                synchronized(CollectorDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CollectorDatabase::class.java,
                        "collector_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        private val roomCallback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDBAsyncTask(instance).execute()
            }
        }

        //NOTE: The database will be created once a command is done. The way this app handles this
        //without cutting off any inserts is to do a viewModel.observe once the first fragment is
        //created
        private class PopulateDBAsyncTask(database: CollectorDatabase?): AsyncTask<Unit, Unit, Unit>() {

            private val regionDao = database?.regionDao()
            private val conditionDao = database?.conditionDao()
            private val typeDao = database?.typeDao()

            override fun doInBackground(vararg params: Unit?) {
                    //Add regions
                GlobalScope.launch {
                    //Add regions
                    regionDao?.insert(Region(1, "NTSC-U", "America"))
                    regionDao?.insert(Region(2,  "PAL", "Europe"))
                    regionDao?.insert(Region(3, "NTSC-J", "Japan"))

                    //Add types
                    typeDao?.insert(Type(1, "DI", "Disc/Game"))
                    typeDao?.insert(Type(2, "CO", "Console"))
                    typeDao?.insert(Type(3, "AC", "Accessory"))

                    //Add conditions
                    val count = typeDao?.getCount()!!
                    var id = 1
                    val codeList = listOf("M", "NM", "VG", "F", "P")
                    val nameList = listOf("Mint", "Near Mint", "Very Good", "Fair", "Poor")
                    for (i in 1..count) {
                        for (j in 0..4) {
                            conditionDao?.insert(Condition(id, codeList[j], nameList[j], i))
                            id++
                        }
                    }
                }
            }
        }
    }
}