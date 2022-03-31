package com.llw.goodnews.repository

import com.llw.goodnews.network.NetworkRequest
import com.llw.goodnews.utils.Constant.CODE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException
import javax.inject.Inject

/**
 * 疫情新闻
 * @author llw
 */
@ViewModelScoped
class EpidemicNewsRepository @Inject constructor(): BaseRepository() {

    fun getEpidemicNews() = fire(Dispatchers.IO) {
        val epidemicNews = NetworkRequest.getEpidemicNews()
        if (epidemicNews.code == CODE) Result.success(epidemicNews)
        else Result.failure(RuntimeException("getNews response code is ${epidemicNews.code} msg is ${epidemicNews.msg}"))
    }
}