package com.llw.goodnews.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * EasyDate
 *
 * @author llw
 * @date 2022/4/4 12:50
 */
object EasyDate {
    private const val STANDARD_TIME = "yyyy-MM-dd HH:mm:ss"
    private const val FULL_TIME = "yyyy-MM-dd HH:mm:ss.SSS"
    private const val YEAR_MONTH_DAY = "yyyy-MM-dd"
    private const val YEAR_MONTH_DAY_CN = "yyyy年MM月dd号"
    private const val HOUR_MINUTE_SECOND = "HH:mm:ss"
    private const val HOUR_MINUTE_SECOND_CN = "HH时mm分ss秒"
    private const val YEAR = "yyyy"
    private const val MONTH = "MM"
    private const val DAY = "dd"
    private const val HOUR = "HH"
    private const val MINUTE = "mm"
    private const val SECOND = "ss"
    private const val MILLISECOND = "SSS"
    private const val YESTERDAY = "昨天"
    private const val TODAY = "今天"
    private const val TOMORROW = "明天"
    private const val SUNDAY = "星期日"
    private const val MONDAY = "星期一"
    private const val TUESDAY = "星期二"
    private const val WEDNESDAY = "星期三"
    private const val THURSDAY = "星期四"
    private const val FRIDAY = "星期五"
    private const val SATURDAY = "星期六"
    private val weekDays = arrayOf(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)

    /**
     * 获取标准时间
     *
     * @return 例如 2021-07-01 10:35:53
     */
    val dateTime: String get() = SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(Date())

    /**
     * 获取完整时间
     *
     * @return 例如 2021-07-01 10:37:00.748
     */
    val fullDateTime: String get() = SimpleDateFormat(FULL_TIME, Locale.CHINESE).format(Date())

    /**
     * 获取年月日(今天)
     *
     * @return 例如 2021-07-01
     */
    val theYearMonthAndDay: String
        get() = SimpleDateFormat(YEAR_MONTH_DAY, Locale.CHINESE).format(Date())

    /**
     * 获取年月日
     *
     * @return 例如 2021年07月01号
     */
    val theYearMonthAndDayCn: String
        get() = SimpleDateFormat(YEAR_MONTH_DAY_CN, Locale.CHINESE).format(Date())

    /**
     * 获取年月日
     * @param delimiter 分隔符
     * @return 例如 2021年07月01号
     */
    fun getTheYearMonthAndDayDelimiter(delimiter: CharSequence): String =
        SimpleDateFormat(YEAR + delimiter + MONTH + delimiter + DAY, Locale.CHINESE).format(Date())

    /**
     * 获取时分秒
     *
     * @return 例如 10:38:25
     */
    val hoursMinutesAndSeconds: String get() = SimpleDateFormat(HOUR_MINUTE_SECOND, Locale.CHINESE).format(Date())

    /**
     * 获取时分秒
     *
     * @return 例如 10时38分50秒
     */
    val hoursMinutesAndSecondsCn: String get() = SimpleDateFormat(HOUR_MINUTE_SECOND_CN, Locale.CHINESE).format(Date())

    /**
     * 获取时分秒
     * @param delimiter 分隔符
     * @return 例如 2021/07/01
     */
    fun getHoursMinutesAndSecondsDelimiter(delimiter: CharSequence): String =
        SimpleDateFormat(HOUR + delimiter + MINUTE + delimiter + SECOND, Locale.CHINESE).format(Date())

    /**
     * 获取年
     *
     * @return 例如 2021
     */
    val year: String get() = SimpleDateFormat(YEAR, Locale.CHINESE).format(Date())

    /**
     * 获取月
     *
     * @return 例如 07
     */
    val month: String get() = SimpleDateFormat(MONTH, Locale.CHINESE).format(Date())

    /**
     * 获取天
     *
     * @return 例如 01
     */
    val day: String get() = SimpleDateFormat(DAY, Locale.CHINESE).format(Date())

    /**
     * 获取小时
     *
     * @return 例如 10
     */
    val hour: String get() = SimpleDateFormat(HOUR, Locale.CHINESE).format(Date())

    /**
     * 获取分钟
     *
     * @return 例如 40
     */
    val minute: String get() = SimpleDateFormat(MINUTE, Locale.CHINESE).format(Date())

    /**
     * 获取秒
     *
     * @return 例如 58
     */
    val second: String get() = SimpleDateFormat(SECOND, Locale.CHINESE).format(Date())

    /**
     * 获取毫秒
     *
     * @return 例如 666
     */
    val milliSecond: String get() = SimpleDateFormat(MILLISECOND, Locale.CHINESE).format(Date())

    /**
     * 获取时间戳
     *
     * @return 例如 1625107306051
     */
    val timestamp: Long get() = System.currentTimeMillis()

    /**
     * 将时间转换为时间戳
     *
     * @param time 例如 2021-07-01 10:44:11
     * @return 1625107451000
     */
    fun dateToStamp(time: String?): Long {
        val simpleDateFormat = SimpleDateFormat(STANDARD_TIME, Locale.CHINESE)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Objects.requireNonNull(date)!!.time
    }

    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis 例如 1625107637084
     * @return 例如 2021-07-01 10:47:17
     */
    fun stampToDate(timeMillis: Long): String = SimpleDateFormat(STANDARD_TIME, Locale.CHINESE).format(Date(timeMillis))

    /**
     * 获取第二天凌晨0点时间戳
     * @return
     */
    fun getMillisNextEarlyMorning(): Long {
        val cal = Calendar.getInstance()
        //日期加1
        cal.add(Calendar.DAY_OF_YEAR, 1)
        //时间设定到0点整
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    /**
     * 获取今天是星期几
     *
     * @return 例如 星期四
     */
    val todayOfWeek: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = Date()
            var index = cal[Calendar.DAY_OF_WEEK] - 1
            if (index < 0) {
                index = 0
            }
            return weekDays[index]
        }

    /**
     * 根据输入的日期时间计算是星期几
     *
     * @param dateTime 例如 2021-06-20
     * @return 例如 星期日
     */
    fun getWeek(dateTime: String): String {
        val cal = Calendar.getInstance()
        if ("" == dateTime) {
            cal.time = Date(System.currentTimeMillis())
        } else {
            val sdf = SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault())
            var date: Date?
            try {
                date = sdf.parse(dateTime)
            } catch (e: ParseException) {
                date = null
                e.printStackTrace()
            }
            if (date != null) {
                cal.time = Date(date.time)
            }
        }
        return weekDays[cal[Calendar.DAY_OF_WEEK] - 1]
    }

    /**
     * 获取输入日期的昨天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-06-30
     */
    fun getYesterday(date: Date?): String {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        date = calendar.time
        return SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date)
    }

    /**
     * 获取输入日期的明天
     *
     * @param date 例如 2021-07-01
     * @return 例如 2021-07-02
     */
    fun getTomorrow(date: Date?): String {
        var date = date
        val calendar: Calendar = GregorianCalendar()
        calendar.time = date
        calendar.add(Calendar.DATE, +1)
        date = calendar.time
        return SimpleDateFormat(YEAR_MONTH_DAY, Locale.getDefault()).format(date)
    }

    /**
     * 根据年月日计算是星期几并与当前日期判断  非昨天、今天、明天 则以星期显示
     *
     * @param dateTime 例如 2021-07-03
     * @return 例如 星期六
     */
    fun getDayInfo(dateTime: String): String {
        val dayInfo: String
        val yesterday = getYesterday(Date())
        val today = theYearMonthAndDay
        val tomorrow = getTomorrow(Date())
        dayInfo = if (dateTime == yesterday) {
            YESTERDAY
        } else if (dateTime == today) {
            TODAY
        } else if (dateTime == tomorrow) {
            TOMORROW
        } else {
            getWeek(dateTime)
        }
        return dayInfo
    }

    //把日期设置为当月第一天
    //日期回滚一天，也就是最后一天
    /**
     * 获取本月天数
     *
     * @return 例如 31
     */
    val currentMonthDays: Int
        get() {
            val calendar = Calendar.getInstance()
            //把日期设置为当月第一天
            calendar[Calendar.DATE] = 1
            //日期回滚一天，也就是最后一天
            calendar.roll(Calendar.DATE, -1)
            return calendar[Calendar.DATE]
        }

    /**
     * 获得指定月的天数
     *
     * @param year  例如 2021
     * @param month 例如 7
     * @return 例如 31
     */
    fun getMonthDays(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month - 1
        //把日期设置为当月第一天
        calendar[Calendar.DATE] = 1
        //日期回滚一天，也就是最后一天
        calendar.roll(Calendar.DATE, -1)
        return calendar[Calendar.DATE]
    }
}