package com.llw.goodnews.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.llw.goodnews.db.bean.EpidemicNews

@Dao
interface EpidemicNewsDao {

    @Query("SELECT * FROM `epidemicnews` WHERE id LIKE :id LIMIT 1")
    suspend fun getNews(id: Int = 1): EpidemicNews

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(desc: EpidemicNews?)
}