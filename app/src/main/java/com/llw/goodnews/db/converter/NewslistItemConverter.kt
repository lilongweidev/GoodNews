package com.llw.goodnews.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.llw.goodnews.db.bean.NewslistItem

/**
 * 转换器
 * @description NewslistItemConverter
 * @author llw
 */
class NewslistItemConverter {

    @TypeConverter
    fun stringToObject(value: String): List<NewslistItem> {
        val listType = object : TypeToken<List<NewslistItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<Any>): String = Gson().toJson(list)

}