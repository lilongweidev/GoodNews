package com.llw.goodnews.repository

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
 * @date 2022/5/2 12:11
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

}