package com.llw.goodnews.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 网络请求
 * @author llw
 */
object NetworkRequest {

    /**
     * 创建服务
     */
    private val service = ServiceCreator.create(ApiService::class.java)

    //通过await()函数将getNews()函数也声明成挂起函数。使用协程
    suspend fun getEpidemicNews() = service.getEpidemicNews().await()

    //获取社会新闻
    suspend fun getSocialNews() = service.getSocialNews().await()

    //获取军事新闻
    suspend fun getMilitaryNews() = service.getMilitaryNews().await()

    //获取科技新闻
    suspend fun getTechnologyNews() = service.getTechnologyNews().await()

    //获取财经新闻
    suspend fun getFinanceNews() = service.getFinanceNews().await()

    //获取娱乐新闻
    suspend fun getAmusementNews() = service.getAmusementNews().await()

    /**
     * Retrofit网络返回处理
     */
    private suspend fun <T> Call<T>.await(): T = suspendCoroutine {
        enqueue(object : Callback<T> {
            //正常返回
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) it.resume(body)
                else it.resumeWithException(RuntimeException("response body is null"))
            }

            //异常返回
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }
        })
    }
}