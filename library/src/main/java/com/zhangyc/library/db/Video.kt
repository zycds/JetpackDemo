package com.zhangyc.library.db

class Video(
    id: Int,
    title: String?,
    artist: String?,
    displayName: String?,
    size: Long?,
    dateModify: String?,
    path: String
) : Media(
    id, title, artist, displayName
    , size, dateModify, path
)