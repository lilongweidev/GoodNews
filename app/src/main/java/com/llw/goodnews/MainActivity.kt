package com.llw.goodnews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.llw.goodnews.db.bean.NewsItem
import com.llw.goodnews.ui.theme.GoodNewsTheme
import com.llw.goodnews.utils.EasyDate
import com.llw.goodnews.utils.showToast
import com.llw.goodnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

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

    val dataState = viewModel.result.observeAsState()

    dataState.value?.let { result ->
        result.getOrNull()?.newslist?.get(0)?.news?.let { MainScreen(it) }
    }
}

@Composable
private fun MainScreen(news: List<NewsItem>) {
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
        BodyContent(news, Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(news: List<NewsItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.padding(8.dp)
    ) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        initData()
    }
}