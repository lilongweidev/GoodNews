package com.llw.goodnews.db.dao

import androidx.room.*
import com.llw.goodnews.db.bean.CollectionNews


@Dao
interface CollectionNewsDao {

    @Query("SELECT * FROM `collectionnews`")
    suspend fun getCollectionNews(): List<CollectionNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collectionNews: CollectionNews)

    @Delete
    suspend fun delete(collectionNews: CollectionNews)
}