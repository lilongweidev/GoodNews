package com.llw.goodnews.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.llw.goodnews.ui.pages.EpidemicNewsListPage
import com.llw.goodnews.ui.pages.HomePage
import com.llw.goodnews.ui.pages.PageConstant.EPIDEMIC_NEWS_LIST_PAGE
import com.llw.goodnews.ui.pages.PageConstant.HOME_PAGE
import com.llw.goodnews.ui.pages.PageConstant.RISK_ZONE_DETAILS_PAGE
import com.llw.goodnews.ui.pages.PageConstant.WEB_VIEW_PAGE
import com.llw.goodnews.ui.pages.RiskZoneDetailsPage
import com.llw.goodnews.ui.pages.WebViewPage
import com.llw.goodnews.ui.theme.GoodNewsTheme
import com.llw.goodnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 主页面
 */
@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: MainViewModel = viewModel()
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = HOME_PAGE,
                        //进入动画
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth },
                                animationSpec = tween(500)
                            )
                        },
                        //退出动画
                        exitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { fullWidth -> -fullWidth },
                                animationSpec = tween(500)
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> -fullWidth },
                                animationSpec = tween(500)
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { fullWidth -> fullWidth },
                                animationSpec = tween(500)
                            )
                        }
                    ) {
                        //主页面
                        composable(HOME_PAGE) {
                            HomePage()
                        }
                        //疫情新闻列表页面
                        composable(EPIDEMIC_NEWS_LIST_PAGE) {
                            EpidemicNewsListPage(navController, viewModel)
                        }
                        //风险区详情页面
                        composable(
                            "$RISK_ZONE_DETAILS_PAGE/{title}/{stringList}",
                            arguments = listOf(
                                navArgument("title") {
                                    type = NavType.StringType //数据类型
                                },
                                navArgument("stringList") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val title = it.arguments?.getString("title") ?: "风险区详情"
                            val stringList = it.arguments?.getString("stringList")
                            RiskZoneDetailsPage(navController, title, stringList)
                        }
                        //WebView页面
                        composable(
                            "$WEB_VIEW_PAGE/{title}/{url}",
                            arguments = listOf(
                                navArgument("title") {
                                    type = NavType.StringType
                                },
                                navArgument("url") {
                                    type = NavType.StringType
                                },
                            )
                        ) {
                            val title = it.arguments?.getString("title") ?: "WebView页面"
                            val url = it.arguments?.getString("url") ?: "WebViewUrl"
                            WebViewPage(navController, title, url)
                        }
                    }
                }
            }
        }
    }
}