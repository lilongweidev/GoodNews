package com.llw.goodnews.repository

import com.llw.goodnews.App
import com.llw.goodnews.db.bean.CollectionNews
import com.llw.goodnews.db.bean.News
import com.llw.goodnews.network.NetworkRequest
import com.llw.goodnews.utils.Constant.CODE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * 新闻数据
 * @description HomeRepository
 * @author llw
 */
@ViewModelScoped
class HomeRepository @Inject constructor() : BaseRepository() {

    /**
     * 获取社会新闻
     */
    fun getSocialNews() = fire(Dispatchers.IO) {
        val news = NetworkRequest.getSocialNews()
        if (news.code == CODE) Result.success(news)
        else Result.failure(RuntimeException("getNews response code is ${news.code} msg is ${news.msg}"))
    }

    /**
     * 获取军事新闻
     */
    fun getMilitaryNews() = fire(Dispatchers.IO) {
        val news = NetworkRequest.getMilitaryNews()
        if (news.code == CODE) Result.success(news)
        else Result.failure(RuntimeException("getNews response code is ${news.code} msg is ${news.msg}"))
    }

    /**
     * 科技新闻
     */
    fun getTechnologyNews() = fire(Dispatchers.IO) {
        val news = NetworkRequest.getTechnologyNews()
        if (news.code == CODE) Result.success(news)
        else Result.failure(RuntimeException("getNews response code is ${news.code} msg is ${news.msg}"))
    }

    /**
     * 财经新闻
     */
    fun getFinanceNews() = fire(Dispatchers.IO) {
        val news = NetworkRequest.getFinanceNews()
        if (news.code == CODE) Result.success(news)
        else Result.failure(RuntimeException("getNews response code is ${news.code} msg is ${news.msg}"))
    }

    /**
     * 娱乐新闻
     */
    fun getAmusementNews() = fire(Dispatchers.IO) {
        val news = NetworkRequest.getAmusementNews()
        if (news.code == CODE) Result.success(news)
        else Result.failure(RuntimeException("getNews response code is ${news.code} msg is ${news.msg}"))
    }

    /**
     * 获取收藏新闻
     */
    fun getCollectionNews() = fire(Dispatchers.IO) {
        val collectionNews = App.db.collectionNewsDao().getCollectionNews()
        if (collectionNews.isNotEmpty()) Result.success(collectionNews)
        else Result.failure(RuntimeException("getCollectionNews response is null"))
    }

    /**
     * 保存新闻数据
     */
    private suspend fun insertCollectionNew(collectionNews: CollectionNews) =
        App.db.collectionNewsDao().insert(collectionNews)

    /**
     * 删除新闻数据
     */
    private suspend fun deleteCollectionNew(collectionNews: CollectionNews) =
        App.db.collectionNewsDao().delete(collectionNews)
}