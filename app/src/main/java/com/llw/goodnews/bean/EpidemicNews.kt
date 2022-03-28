package com.llw.goodnews.bean

data class EpidemicNews(val msg: String = "",
                        val code: Int = 0,
                        val newslist: List<NewslistItem>?)

data class NewslistItem(val news: List<NewsItem>?,
                        val desc: Desc,
                        val riskarea: Riskarea)

data class NewsItem(val summary: String = "",
                    val sourceUrl: String = "",
                    val id: Int = 0,
                    val title: String = "",
                    val pubDate: Long = 0,
                    val pubDateStr: String = "",
                    val infoSource: String = "")

data class Desc(val curedCount: Int = 0,
                val seriousCount: Int = 0,
                val currentConfirmedIncr: Int = 0,
                val midDangerCount: Int = 0,
                val suspectedIncr: Int = 0,
                val seriousIncr: Int = 0,
                val confirmedIncr: Int = 0,
                val globalStatistics: GlobalStatistics,
                val deadIncr: Int = 0,
                val suspectedCount: Int = 0,
                val currentConfirmedCount: Int = 0,
                val confirmedCount: Int = 0,
                val modifyTime: Long = 0,
                val createTime: Long = 0,
                val curedIncr: Int = 0,
                val yesterdaySuspectedCountIncr: Int = 0,
                val foreignStatistics: ForeignStatistics,
                val highDangerCount: Int = 0,
                val id: Int = 0,
                val deadCount: Int = 0,
                val yesterdayConfirmedCountIncr: Int = 0)

data class Riskarea(val high: List<String>?,
                    val mid: List<String>?)

data class GlobalStatistics(val currentConfirmedCount: Int = 0,
                            val confirmedCount: Int = 0,
                            val curedCount: Int = 0,
                            val currentConfirmedIncr: Int = 0,
                            val confirmedIncr: Int = 0,
                            val curedIncr: Int = 0,
                            val deadCount: Int = 0,
                            val deadIncr: Int = 0,
                            val yesterdayConfirmedCountIncr: Int = 0)

data class ForeignStatistics(val currentConfirmedCount: Int = 0,
                             val confirmedCount: Int = 0,
                             val curedCount: Int = 0,
                             val currentConfirmedIncr: Int = 0,
                             val suspectedIncr: Int = 0,
                             val confirmedIncr: Int = 0,
                             val curedIncr: Int = 0,
                             val deadCount: Int = 0,
                             val deadIncr: Int = 0,
                             val suspectedCount: Int = 0)