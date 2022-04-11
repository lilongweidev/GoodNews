package com.llw.goodnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.llw.goodnews.db.bean.Desc
import com.llw.goodnews.db.bean.NewsItem
import com.llw.goodnews.db.dao.DescDao
import com.llw.goodnews.db.dao.NewsItemDao

/**
 * 数据库
 * @description AppDatabase
 * @author llw
 * @date 2022/4/4 1:33
 */
@Database(entities = [
    NewsItem::class,
    Desc::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsItemDao(): NewsItemDao

    abstract fun descDao(): DescDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        private const val DATABASE_NAME = "good_news.db"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addMigrations()
                    .build()
                    .also { instance = it }
            }
        }
    }
}