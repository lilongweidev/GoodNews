package com.llw.goodnews.db.bean

data class News(val msg: String = "",
                val code: Int = 0,
                val newslist: List<Newslist>)

data class Newslist(val picUrl: String = "",
                        val ctime: String = "",
                        val description: String = "",
                        val id: String = "",
                        val source: String = "",
                        val title: String = "",
                        val url: String = "")