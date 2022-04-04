package com.llw.goodnews.db

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.llw.goodnews.App
import com.llw.goodnews.db.bean.NewsItem
import com.llw.goodnews.db.dao.NewsItemDao

/**
 * 数据库
 * @description AppDatabase
 * @author llw
 * @date 2022/4/4 1:33
 */
@Database(entities = [NewsItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsItemDao(): NewsItemDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        private const val DATABASE_NAME = "good_news.db"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .build().also { instance = it }
            }
        }
    }
}