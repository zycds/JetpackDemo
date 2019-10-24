package com.zhangyc.jetpackdemo.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhangyc.jetpackdemo.App
import com.zhangyc.jetpackdemo.entities.Entities

@Database(entities = [Entities.User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

    companion object {

        @Volatile
        private var instance: AppDataBase? = null

        fun getDBInstance(): AppDataBase {

            if (instance == null) {
                synchronized(AppDataBase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            App.instance().applicationContext,
                            AppDataBase::class.java,
                            "jetpack.db"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return instance!!
        }

    }


}