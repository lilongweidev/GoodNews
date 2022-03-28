package com.llw.goodnews.repository

import androidx.lifecycle.liveData
import kotlin.coroutines.CoroutineContext

/**
 * 通常存储库
 * @author llw
 */
open class BaseRepository {

    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            //通知数据变化
            emit(result)
        }
}