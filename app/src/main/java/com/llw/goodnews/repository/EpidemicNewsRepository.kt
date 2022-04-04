package com.llw.goodnews.repository

import android.util.Log
import com.llw.goodnews.App
import com.llw.goodnews.db.bean.EpidemicNews
import com.llw.goodnews.db.bean.NewslistItem
import com.llw.goodnews.network.NetworkRequest
import com.llw.goodnews.utils.Constant.CODE
import com.llw.goodnews.utils.Constant.REQUEST_TIMESTAMP
import com.llw.goodnews.utils.Constant.SUCCESS
import com.llw.goodnews.utils.EasyDataStore
import com.llw.goodnews.utils.EasyDate
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * 疫情新闻
 * @author llw
 */
@ViewModelScoped
class EpidemicNewsRepository @Inject constructor() : BaseRepository() {

    private val TAG = "EpidemicNewsRepository"

    companion object {
        lateinit var epidemicNews: EpidemicNews
    }

    fun getEpidemicNews() = fire(Dispatchers.IO) {
        //判断是否是今天第一次请求网络
        if (EasyDate.timestamp <= EasyDataStore.getData(REQUEST_TIMESTAMP, 1649049670500)) {
            //当前时间未超过次日0点，从本地获取数据库
            Log.d(TAG, "getEpidemicNews: 从数据库中获取")
            epidemicNews = getLocalForNews()
        } else {
            //从网络中获取
            Log.d(TAG, "getEpidemicNews: 从网络中获取")
            epidemicNews = NetworkRequest.getEpidemicNews()
            //保存到本地数据库
            saveNews(epidemicNews)
        }
        if (epidemicNews.code == CODE) Result.success(epidemicNews)
        else Result.failure(RuntimeException("getNews response code is ${epidemicNews.code} msg is ${epidemicNews.msg}"))
    }

    /**
     * 保存到本地数据库
     */
    private suspend fun saveNews(epidemicNews: EpidemicNews) {
        Log.d(TAG, "saveNews: 保存到本地数据库")
        EasyDataStore.putData(REQUEST_TIMESTAMP, EasyDate.getMillisNextEarlyMorning())
        App.db.newsItemDao().deleteAll()
        App.db.newsItemDao().insertAll(epidemicNews.newslist?.get(0)?.news)
    }

    /**
     * 从本地数据库中加载
     */
    private suspend fun getLocalForNews() = EpidemicNews(SUCCESS, CODE, listOf(NewslistItem(App.db.newsItemDao().getAll(), null, null)))
}