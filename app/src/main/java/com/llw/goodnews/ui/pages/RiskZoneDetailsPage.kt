package com.llw.goodnews.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.llw.goodnews.R
import com.llw.goodnews.utils.showToast
import java.util.ArrayList

/**
 * 风险区详情页面
 */
@Composable
fun RiskZoneDetailsPage(navController: NavHostController, title: String, stringList: String?) {
    Scaffold(
        topBar = {
            //顶部应用栏
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "ArrowBack"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { "Settings".showToast() },
                        modifier = Modifier.alpha(0f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                        )
                    }
                },
                elevation = 4.dp
            )
        }
    ) {
        val list = Gson().fromJson<List<String>>(stringList)
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.background(colorResource(id = R.color.gray_white))
        ) {
            itemsIndexed(list) { index, content ->
                Text(text = "${index + 1}. $content", modifier = Modifier
                    .clickable { content.showToast() }
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.white))
                    .padding(16.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }
        }
    }
}

inline fun <reified T : Any> Gson.fromJson(json: String?): T {
    return Gson().fromJson(json, T::class.java)
}

