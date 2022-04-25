package com.llw.goodnews.ui.pages

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

/**
 * WebView页面
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(navController: NavHostController, title: String, url: String) {
    Scaffold(
        topBar = {
            //顶部应用栏
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSecondary,
                        overflow = TextOverflow.Ellipsis, //超出省略
                        maxLines = 1 //单行显示
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
                elevation = 4.dp
            )
        }
    ) {
        val mWebViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("webView", "加载开始")
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("webViewT", "加载完成")
            }
        }
        val mWebViewChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                Log.d("webView", "加载：$newProgress")
                super.onProgressChanged(view, newProgress)
            }
        }
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    useWideViewPort = true
                    javaScriptCanOpenWindowsAutomatically = true
                    domStorageEnabled = true
                    loadsImagesAutomatically = true
                    loadWithOverviewMode = true
                }
                webViewClient = mWebViewClient
                webChromeClient = mWebViewChromeClient
                loadUrl(url)
            }
        })
    }
}