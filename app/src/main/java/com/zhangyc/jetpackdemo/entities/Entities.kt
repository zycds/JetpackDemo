package com.zhangyc.jetpackdemo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class Entities {
//{"data":{"admin":false,"chapterTops":[],"collectIds":[],"email":"","icon":"","id":32675,"nickname":"usern1231",
// "password":"","publicName":"usern1231","token":"","type":0,"username":"usern1231"},"errorCode":0,"errorMsg":""}
    @Entity(tableName = "table_user")
    class User {
        @PrimaryKey(autoGenerate = true)
        var usrId : Int = 0
        @ColumnInfo(name = "username")
        var username : String? = null
        @ColumnInfo(name = "password")
        var password : String? = null
    }

}