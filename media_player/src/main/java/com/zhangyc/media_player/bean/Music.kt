package com.zhangyc.media_player.bean

import com.zhangyc.library.db.Media

class Music(id : Int, title : String?, artist : String?, displayName : String?, size : Long?, dateModify : String?) : Media(id, title, artist, displayName
        , size, dateModify)