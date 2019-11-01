package com.zhangyc.library.db

open class Media {
    var id : Int = 0
    var title :String? = null
    var artist : String? = null
    var displayName : String? = null

    constructor(id : Int, title : String?, artist : String?, displayName : String?) {
        this.id = id
        this.title = title
        this.artist = artist
        this.displayName = displayName
    }
}