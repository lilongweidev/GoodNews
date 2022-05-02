package com.llw.goodnews.ui.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import com.google.gson.Gson
import com.llw.goodnews.R
import com.llw.goodnews.db.bean.Newslist
import com.llw.goodnews.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * 首页
 */
@Composable
fun HomeItem(mNavController: NavHostController, viewModel: HomeViewModel) {
    TabViewPager(mNavController, viewModel)
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabViewPager(mNavController: NavHostController, viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 50.dp)
    ) {
        val pages by mutableStateOf(
            listOf("社会", "军事", "科技", "财经", "娱乐")
        )
        val pagerState = rememberPagerState(initialPage = 0)//初始化页面，0就表示第一个页面
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            // 使用提供的 pagerTabIndicatorOffset 修饰符自定义指示器
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black)
        ) {
            //给全部页面添加标签栏
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,//是否选中
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    modifier = Modifier.alpha(0.9f),//透明度
                    enabled = true,//是否启用
                    selectedContentColor = colorResource(id = R.color.black),//选中的颜色
                    unselectedContentColor = colorResource(id = R.color.gray),//未选中的颜色
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,//用于控制或观察viewpage状态的状态对象。
            modifier = Modifier.padding(top = 4.dp),
            itemSpacing = 2.dp
        ) { page ->
            val dataState = viewModel.result.observeAsState()
            when (page) {
                0 -> dataState.value?.let {
                    ShowNewsList(mNavController, it.getOrNull()!!.newslist)
                }
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Page: $page",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowNewsList(mNavController: NavHostController, newslist: List<Newslist>) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .padding(8.dp)
            .navigationBarsPadding()
    ) {
        items(newslist) { new ->
            Row(modifier = Modifier
                .clickable {
                    val encodedUrl = URLEncoder.encode(new.url, StandardCharsets.UTF_8.toString())
                    mNavController.navigate("${PageConstant.WEB_VIEW_PAGE}/${new.title}/$encodedUrl")
                }
                .padding(8.dp)
            ) {
                AsyncImage(
                    model = new.picUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp),
                    contentScale = ContentScale.FillBounds
                )
                Column(modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)) {
                    Text(
                        text = new.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                    Row(modifier = Modifier.padding(0.dp, 10.dp)) {
                        Text(text = new.source, fontSize = 12.sp)
                        Text(
                            text = new.ctime,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(8.dp, 0.dp)
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier.padding(horizontal = 8.dp),
                color = colorResource(id = R.color.black).copy(alpha = 0.08f)
            )
        }
    }


}

