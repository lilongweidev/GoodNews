package com.llw.goodnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.llw.goodnews.db.bean.Desc
import com.llw.goodnews.db.bean.NewsItem
import com.llw.goodnews.db.bean.NewslistItem
import com.llw.goodnews.ui.theme.GoodNewsTheme
import com.llw.goodnews.utils.showToast
import com.llw.goodnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    initData()
                }
            }
        }
    }
}


@Composable
fun initData(viewModel: MainViewModel = viewModel()) {
    viewModel.getNews()
    viewModel.result.observeAsState().value?.let { result ->
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
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { "Person".showToast() }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Person"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { "Settings".showToast() }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                        )
                    }
                },
                elevation = 4.dp
            )
        }
    ) { innerPadding ->
        BodyContent(newslistItem.news, newslistItem.desc, Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(
    news: List<NewsItem>,
    desc: Desc,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { viewModel.getNews(true) },
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
            //descItemPlus(desc)
            items(news) { new ->
                Column(modifier = Modifier.padding(8.dp)) {
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
                    color = colorResource(id = R.color.black).copy(alpha = 0.08f)
                )
            }
        }
    }
}

data class DescItem(var title: String, var current: Int, var yesterday: Int)

data class GroupItem(val descItem: DescItem?, val isEmpty: Boolean)

private fun LazyListScope.descItemPlus(desc: Desc) {
    //构建一个DescItemList
    val descList = mutableListOf<DescItem>().apply {
        add(DescItem("现存确诊人数", desc.currentConfirmedCount, desc.currentConfirmedIncr))
        add(DescItem("累计确诊人数", desc.confirmedCount, desc.confirmedIncr))
        add(DescItem("累计治愈人数", desc.curedCount, desc.curedIncr))
        add(DescItem("累计死亡人数", desc.deadCount, desc.deadIncr))
        add(DescItem("现存无症状人数", desc.seriousCount, desc.seriousIncr))
    }
    //网格Items
    val gradItems = mutableListOf<List<GroupItem>>()
    var index = 0
    //网格是行与列组成，显示2列
    val columnNum = 2
    //计算显示几行
    val rowNum = ceil(descList.size.toFloat() / columnNum).toInt()
    //遍历行
    for (i in 0 until rowNum) {
        val rowItems = mutableListOf<GroupItem>()
        //遍历列
        for (j in 0 until columnNum) {
            if (index.inc() <= descList.size) {
                rowItems.add(GroupItem(descList[index++], false))
            }
        }

        //如果未填充满，则显示占位
        val itemEmpty = columnNum - rowItems.size
        for (j in 0 until itemEmpty) {
            rowItems.add(GroupItem(null, true))
        }
        gradItems.add(rowItems)
    }
    //显示数据
    items(gradItems) { gradItem ->
        Row {
            for (gird in gradItem) {
                if (gird.isEmpty) {
                    Box(modifier = Modifier.weight(1f))
                } else {
                    Box(modifier = Modifier.weight(1f)) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = 2.dp,
                            backgroundColor = Color.White
                        ) {
                            val descItem = gird.descItem!!
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.Center,//设置垂直居中对齐
                                horizontalAlignment = Alignment.CenterHorizontally//设置水平居中对齐
                            ) {
                                Text(text = descItem.title)
                                Text(
                                    text = descItem.current.toString(),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "较昨日 ${descItem.yesterday}")
                            }
                        }
                    }
                }
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
                        modifier = Modifier.fillMaxSize().weight(1f),
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
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))) {
                                append("较昨日 ")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                                append(desc.currentConfirmedIncr.addSymbols())
                            }
                        })
                    }

                    Column(
                        modifier = Modifier.fillMaxSize().weight(1f),
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
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))) {
                                append("较昨日 ")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                                append(desc.confirmedIncr.addSymbols())
                            }
                        })
                    }
                }
                Row(modifier = Modifier.padding(12.dp)) {
                    Column(
                        modifier = Modifier.fillMaxSize().weight(1f),
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
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))) {
                                append("较昨日 ")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                                append(desc.curedIncr.addSymbols())
                            }
                        })
                    }

                    Column(
                        modifier = Modifier.fillMaxSize().weight(1f),
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
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = colorResource(id = R.color.gray))) {
                                append("较昨日 ")
                            }
                            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
                                append(desc.deadIncr.addSymbols())
                            }
                        })
                    }
                }
            }
        }
    }
}

fun Int.addSymbols(): String = if (this > 0.0 && this != 0) "+$this" else "$this"

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        initData()
    }
}