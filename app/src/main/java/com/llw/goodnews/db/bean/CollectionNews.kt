package com.llw.goodnews.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CollectionNews(
    @PrimaryKey val uid: Int,
    val picUrl: String = "",
    val ctime: String = "",
    val description: String = "",
    val id: String = "",
    val source: String = "",
    val title: String = "",
    val url: String = ""
)
