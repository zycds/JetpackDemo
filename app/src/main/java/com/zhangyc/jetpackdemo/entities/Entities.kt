package com.zhangyc.jetpackdemo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Entities {

    @Entity(tableName = "table_user")
    data class User (
        @PrimaryKey(autoGenerate = true)
        var usrId : Int,
        @ColumnInfo(name = "username")
        var username : String,
        @ColumnInfo(name = "password")
        var password : String
    )

}