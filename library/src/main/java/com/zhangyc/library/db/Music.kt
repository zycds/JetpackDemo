package com.zhangyc.library.db


class Music(
    id: Int,
    title: String?,
    artist: String?,
    displayName: String?,
    size: Long?,
    dateModify: String?,
    path: String,
    var albumId : String?
) : Media(
    id, title, artist, displayName
    , size, dateModify, path
)