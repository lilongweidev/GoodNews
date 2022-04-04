package com.llw.goodnews.repository

import android.util.Log
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
                Log.e("BaseRepository", "fire: "+ e.message)
                Result.failure(e)
            }
            //通知数据变化
            emit(result)
        }
}