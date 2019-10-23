package com.zhangyc.jetpackdemo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zhangyc.jetpackdemo.entities.Entities

@Dao
interface UserDao : BaseDao<Entities.User> {

    @Insert
    fun insert(element : Entities.User)

    @Query("select * from table_user")
    fun getAllUsers() : MutableList<Entities.User>


}