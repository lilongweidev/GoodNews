package com.llw.goodnews.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import com.llw.goodnews.R
import com.llw.goodnews.db.bean.Desc
import com.llw.goodnews.db.bean.NewsItem
import com.llw.goodnews.db.bean.NewslistItem
import com.llw.goodnews.db.bean.Riskarea
import com.llw.goodnews.ui.pages.PageConstant.RISK_ZONE_DETAILS_PAGE
import com.llw.goodnews.ui.pages.PageConstant.WEB_VIEW_PAGE
import com.llw.goodnews.utils.showToast
import com.llw.goodnews.viewmodel.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("StaticFieldLeak")
lateinit var mNavController: NavHostController
lateinit var mViewModel: MainViewModel

/**
 * 疫情新闻列表页面
 */
@Composable
fun EpidemicNewsListPage(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    mNavController = navController
    mViewModel = viewModel

    mViewModel.getNews()
    mViewModel.result.observeAsState().value?.let { result ->
        result.getOrNull()?.newslist?.get(0)?.let { MainScreen(it) }
    }
}

@Composable
private fun MainScreen(newslistItem: NewslistItem) {
    Scaffold(
        topBar = {
            //顶部应用栏
            TopAppBar(
                title = {
                    Text(
                        text = "疫情新闻",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSecondary,
                    )
                },
                elevation = 4.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { mNavController.popBackStack() },
                contentColor = Color.White,
                content = { Icon(Icons.Filled.Home, contentDescription = "") })
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        BodyContent(
            newslistItem.desc,
            newslistItem.riskarea,
            newslistItem.news,
            Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyContent(
    desc: Desc, riskarea: Riskarea, news: List<NewsItem>, modifier: Modifier = Modifier
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { mViewModel.getNews(true) },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small,
            )
        }) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier.padding(8.dp)
        ) {
            descItem(desc)
            riskareaItem(riskarea)
            items(news) { new ->
                Column(modifier = Modifier
                    .clickable {
                        val encodedUrl =
                            URLEncoder.encode(new.sourceUrl, StandardCharsets.UTF_8.toString())
                        mNavController.navigate("${WEB_VIEW_PAGE}/${new.title}/$encodedUrl")
                    }
                    .padding(8.dp)) {
                    Text(
                        text = new.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(0.dp, 10.dp)
                    )
                    Text(text = new.summary, fontSize = 12.sp)
                    Row(modifier = Modifier.padding(0.dp, 10.dp)) {
                        Text(text = new.infoSource, fontSize = 12.sp)
                        Text(
                            text = new.pubDateStr,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(8.dp, 0.dp)
                        )
                    }
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = colorResource(id = com.llw.goodnews.R.color.black).copy(alpha = 0.08f)
                )
            }
        }
    }
}

private fun LazyListScope.descItem(desc: Desc) {
    item {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 2.dp,
            backgroundColor = Color.White
        ) {
            Column {
                Row(modifier = Modifier.padding(12.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,//设置垂直居中对齐
                        horizontalAlignment = Alignment.CenterHorizontally//设置水平居中对齐
                    ) {
                        Text(text = "现存确诊人数", fontSize = 12.sp)
                        Text(
                            text = desc.currentConfirmedCount.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.red),
                            modifier = Modifier.padding(0.dp, 4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.gray)
                                )
                            ) {
                                append("较昨日 ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(desc.currentConfirmedIncr.addSymbols())
                            }
                        })
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "累计确诊人数", fontSize = 12.sp)
                        Text(
                            text = desc.confirmedCount.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.dark_red),
                            modifier = Modifier.padding(0.dp, 4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.gray)
                                )
                            ) {
                                append("较昨日 ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(desc.confirmedIncr.addSymbols())
                            }
                        })
                    }
                }
                Row(modifier = Modifier.padding(12.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,//设置垂直居中对齐
                        horizontalAlignment = Alignment.CenterHorizontally//设置水平居中对齐
                    ) {
                        Text(text = "累计治愈人数", fontSize = 12.sp)
                        Text(
                            text = desc.curedCount.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.green),
                            modifier = Modifier.padding(0.dp, 4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.gray)
                                )
                            ) {
                                append("较昨日 ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(desc.curedIncr.addSymbols())
                            }
                        })
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "累计死亡人数", fontSize = 12.sp)
                        Text(
                            text = desc.deadCount.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.gray_black),
                            modifier = Modifier.padding(0.dp, 4.dp)
                        )
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.gray)
                                )
                            ) {
                                append("较昨日 ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append(desc.deadIncr.addSymbols())
                            }
                        })
                    }
                }
            }
        }
    }
}

private fun LazyListScope.riskareaItem(riskarea: Riskarea) {
    item {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 2.dp,
            backgroundColor = Color.White
        ) {

            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            mNavController.navigate(
                                "${RISK_ZONE_DETAILS_PAGE}/高风险区/${
                                    Gson().toJson(
                                        riskarea.high
                                    )
                                }"
                            )
                        }
                        .padding(0.dp, 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "高风险区", fontSize = 12.sp)
                    Text(buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.red)
                            )
                        ) {
                            append("${riskarea.high?.size}")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("个")
                        }
                    })
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            mNavController.navigate(
                                "$RISK_ZONE_DETAILS_PAGE/中风险区/${
                                    Gson().toJson(
                                        riskarea.mid
                                    )
                                }"
                            )
                        }
                        .padding(0.dp, 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "中风险区", fontSize = 12.sp)
                    Text(buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.dark_red)
                            )
                        ) {
                            append("${riskarea.mid?.size}")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("个")
                        }
                    })
                }
            }
        }
    }
}

fun Int.addSymbols(): String = if (this > 0.0 && this != 0) "+$this" else "$this"

