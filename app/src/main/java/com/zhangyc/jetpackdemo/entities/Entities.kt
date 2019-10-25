package com.zhangyc.jetpackdemo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class Entities {
    //{"data":{"admin":false,"chapterTops":[],"collectIds":[],"email":"","icon":"","id":32675,"nickname":"usern1231",
// "password":"","publicName":"usern1231","token":"","type":0,"username":"usern1231"},"errorCode":0,"errorMsg":""}
    @Entity(tableName = "table_user")
    class User : Serializable {
        @PrimaryKey(autoGenerate = true)
        var userId: Int = 0
        @ColumnInfo(name = "username")
        var username: String? = null
        @ColumnInfo(name = "password")
        var password: String? = null
    }

    @Entity(tableName = "table_banner")
    class Banner{
        @PrimaryKey(autoGenerate = true)
        var bId : Int = 0
        @ColumnInfo(name = "courseId")
        var desc = "Android高级进阶直播课免费学习"
        @ColumnInfo(name = "id")
        var id =  23
        @ColumnInfo(name = "imagePath")
        var imagePath =  "https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg"
        @ColumnInfo(name = "isVisible")
        var isVisible =  1
        @ColumnInfo(name = "order")
        var order =  0
        @ColumnInfo(name = "title")
        var title =  "Android高级进阶直播课免费学习"
        @ColumnInfo(name = "type")
        var type =  0
        @ColumnInfo(name = "url")
        var url =  "https://url.163.com/4bj"
    }

    @Entity(tableName = "table_pubAddress")
    class PubAddress{
//        children: [ ],

        @PrimaryKey(autoGenerate = true)
        var pId : Int = 0
        @ColumnInfo(name = "courseId")
        var courseId : Int = 0
        @ColumnInfo(name = "id")
        var id = 0
        @ColumnInfo(name = "name")
        var name = "鸿洋"
        @ColumnInfo(name = "order")
        var order = 190000
        @ColumnInfo(name = "parentChapterId")
        var parentChapterId = 407
        @ColumnInfo(name = "userControlSetTop")
        var userControlSetTop = false
        @ColumnInfo(name = "visible")
        var visible = 1
    }

    data class PublicAHistoryPage(var currentPage : Int, var pageCount : Int ,var total : Int, var datas : MutableList<PublicAHistory>?)


    @Entity(tableName = "table_pubHistory")
    class PublicAHistory {

        @PrimaryKey(autoGenerate = true)
        var addressId : Int = 0
        @ColumnInfo(name = "apkLink")
        var apkLink: String = ""
        @ColumnInfo(name = "audit")
        var audit = 1
        @ColumnInfo(name = "author")
        var author = "鸿洋"
        @ColumnInfo(name = "chapterId")
        var chapterId = 408
        @ColumnInfo(name = "chapterName")
        var chapterName = "鸿洋"
        @ColumnInfo(name = "collect")
        var collect = false
        @ColumnInfo(name = "courseId")
        var courseId = 13
        @ColumnInfo(name = "desc")
        var desc = ""
        @ColumnInfo(name = "envelopePic")
        var envelopePic = ""
        @ColumnInfo(name = "fresh")
        var fresh = false
        @ColumnInfo(name = "id")
        var id = 9747
        @ColumnInfo(name = "link")
        var link = "https://mp.weixin.qq.com/s/4Ie4bkF8qG3yHWaqRIcbgw"
        @ColumnInfo(name = "niceDate")
        var niceDate = "2019-10-16"
        @ColumnInfo(name = "niceShareDate")
        var niceShareDate = "2019-10-17"
        @ColumnInfo(name = "origin")
        var origin = ""
        @ColumnInfo(name = "prefix")
        var prefix =  ""
        @ColumnInfo(name = "projectLink")
        var projectLink = ""
        @ColumnInfo(name = "publishTime")
        var publishTime = 1571155200000
        @ColumnInfo(name = "selfVisible")
        var selfVisible = 0
        @ColumnInfo(name = "shareDate")
        var shareDate = 1571314396000
        @ColumnInfo(name = "shareUser")
        var shareUser = ""
        @ColumnInfo(name = "superChapterId")
        var superChapterId = 408
        @ColumnInfo(name = "superChapterName")
        var superChapterName = "公众号"
        /*tags: [
        {
            name: "公众号",
            url: "/wxarticle/list/408/1"
        }
        ],*/
        @ColumnInfo(name = "title")
        var title = "JakeWharton评价我的代码像是在打地鼠？"
        @ColumnInfo(name = "type")
        var type = 0
        @ColumnInfo(name = "userId")
        var userId = -1
        @ColumnInfo(name = "visible")
        var visible = 1
        @ColumnInfo(name = "zan")
        var zan = 0
    }
}