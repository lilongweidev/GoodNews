package com.llw.goodnews.utils

import android.widget.Toast
import com.llw.goodnews.App


fun String.showToast() = Toast.makeText(App.context, this, Toast.LENGTH_SHORT).show()

fun String.showLongToast() = Toast.makeText(App.context, this, Toast.LENGTH_LONG).show()

fun Int.showToast() = Toast.makeText(App.context, this, Toast.LENGTH_SHORT).show()

fun Int.showLongToast() = Toast.makeText(App.context, this, Toast.LENGTH_LONG).show()