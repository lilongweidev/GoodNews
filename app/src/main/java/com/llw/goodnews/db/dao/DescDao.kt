package com.llw.goodnews.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.llw.goodnews.db.bean.Desc
import com.llw.goodnews.db.bean.NewsItem

@Dao
interface DescDao {

    @Query("SELECT * FROM `desc` WHERE id LIKE :id LIMIT 1")
    suspend fun getDesc(id: Int = 1): Desc

    @Insert
    suspend fun insert(desc: Desc?)

    @Query("DELETE FROM `desc`")
    suspend fun deleteAll()
}