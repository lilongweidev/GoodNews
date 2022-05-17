package com.llw.goodnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.llw.goodnews.db.bean.CollectionNews
import com.llw.goodnews.db.bean.EpidemicNews
import com.llw.goodnews.db.dao.CollectionNewsDao
import com.llw.goodnews.db.dao.EpidemicNewsDao


/**
 * 数据库
 * @description AppDatabase
 * @author llw
 */
@Database(
    entities = [EpidemicNews::class, CollectionNews::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun epidemicNewsDao(): EpidemicNewsDao

    abstract fun collectionNewsDao(): CollectionNewsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        private const val DATABASE_NAME = "good_news.db"

        /**
         * 版本升级迁移到2 新增收藏新闻表
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `collectionnews` " +
                            "(uid INTEGER, " +
                            "picUrl TEXT, " +
                            "ctime TEXT, " +
                            "description TEXT, " +
                            "id TEXT, " +
                            "source TEXT, " +
                            "title TEXT, " +
                            "url TEXT, " +
                            "PRIMARY KEY(`uid`))"
                )
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { instance = it }
            }
        }
    }
}