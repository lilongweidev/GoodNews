package com.llw.goodnews.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.llw.goodnews.db.bean.NewsItem
import kotlinx.coroutines.flow.Flow

/**
 * Dao方法
 * @description NewsItemDao
 * @author llw
 * @date 2022/4/4 1:25
 */
@Dao
interface NewsItemDao {

    @Query("SELECT * FROM newsitem")
    suspend fun getAll(): List<NewsItem>

    @Insert
    suspend fun insertAll(newsItem: List<NewsItem>?)

    @Query("DELETE FROM newsitem")
    suspend fun deleteAll()
}