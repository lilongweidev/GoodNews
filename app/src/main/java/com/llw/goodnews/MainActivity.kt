package com.llw.goodnews

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.llw.goodnews.repository.EpidemicNewsRepository
import com.llw.goodnews.ui.theme.GoodNewsTheme

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
                    EpidemicNewsRepository.getEpidemicNews().observe(this@MainActivity) { result ->
                        val epidemicNews = result.getOrNull()
                        if (epidemicNews != null) {
                            Log.d("TAG", "onCreate: ${epidemicNews.code}")
                            Log.d("TAG", "onCreate: ${epidemicNews.msg}")
                            Log.d("TAG", "onCreate: ${epidemicNews.newslist?.get(0)?.news?.get(0)?.title}")
                            Log.d("TAG", "onCreate: ${epidemicNews.newslist?.get(0)?.news?.get(0)?.summary}")
                        } else {
                            Log.e("TAG", "onCreate: null")
                        }
                    }
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        Greeting("Android")
    }
}