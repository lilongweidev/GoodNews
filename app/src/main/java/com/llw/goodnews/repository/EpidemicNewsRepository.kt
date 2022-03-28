package com.llw.goodnews.repository

import com.llw.goodnews.network.NetworkRequest
import com.llw.goodnews.utils.Constant.CODE
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

/**
 * 疫情新闻
 * @author llw
 */
object EpidemicNewsRepository : BaseRepository() {

    fun getEpidemicNews() = fire(Dispatchers.IO) {
        val epidemicNews = NetworkRequest.getEpidemicNews()
        if (epidemicNews.code == CODE) Result.success(epidemicNews)
        else Result.failure(RuntimeException("getNews response code is ${epidemicNews.code} msg is ${epidemicNews.msg}"))
    }
}