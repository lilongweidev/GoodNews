package com.llw.goodnews.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.llw.goodnews.ui.pages.EpidemicNewsListPage
import com.llw.goodnews.ui.pages.PageConstant.EPIDEMIC_NEWS_LIST_PAGE
import com.llw.goodnews.ui.pages.PageConstant.RISK_ZONE_DETAILS_PAGE
import com.llw.goodnews.ui.pages.RiskZoneDetailsPage
import com.llw.goodnews.ui.theme.GoodNewsTheme
import com.llw.goodnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 主页面
 */
@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: MainViewModel = viewModel()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = EPIDEMIC_NEWS_LIST_PAGE
                    ) {
                        //疫情新闻列表页面
                        composable(EPIDEMIC_NEWS_LIST_PAGE) {
                            EpidemicNewsListPage(navController, viewModel)
                        }
                        //风险区详情页面
                        composable(
                            "$RISK_ZONE_DETAILS_PAGE/{title}/{stringList}",
                            arguments = listOf(
                                navArgument("title"){
                                    type = NavType.StringType //数据类型
                                },
                                navArgument("stringList") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val title = it.arguments?.getString("title") ?: "风险区详情"
                            val stringList = it.arguments?.getString("stringList")
                            RiskZoneDetailsPage(navController,title,stringList)
                        }
                    }
                }
            }
        }
    }
}