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

    /**
     * 获取军事新闻
     */
    @GET("/military/index?key=$API_KEY")
    fun getMilitaryNews(): Call<News>

    /**
     * 获取科技新闻
     */
    @GET("/keji/index?key=$API_KEY")
    fun getTechnologyNews(): Call<News>

    /**
     * 获取财经新闻
     */
    @GET("/caijing/index?key=$API_KEY")
    fun getFinanceNews(): Call<News>

    /**
     * 获取娱乐新闻
     */
    @GET("/huabian/index?key=$API_KEY")
    fun getAmusementNews(): Call<News>


}