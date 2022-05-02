package com.llw.goodnews.network

import com.llw.goodnews.db.bean.EpidemicNews
import com.llw.goodnews.db.bean.News
import com.llw.goodnews.utils.Constant.API_KEY
import retrofit2.Call
import retrofit2.http.GET

/**
 * APi服务接口
 * @author llw
 */
interface ApiService {

    /**
     * 获取疫情新闻
     */
    @GET("/ncov/index?key=$API_KEY")
    fun getEpidemicNews(): Call<EpidemicNews>

    /**
     * 获取社会新闻
     */
    @GET("/social/index?key=$API_KEY")
    fun getSocialNews(): Call<News>
}