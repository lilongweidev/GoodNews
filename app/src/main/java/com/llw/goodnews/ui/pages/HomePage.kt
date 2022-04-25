package com.llw.goodnews.ui.pages

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.llw.goodnews.R
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.llw.goodnews.ui.BottomBarView
import com.llw.goodnews.ui.pages.PageConstant.COLLECTION_ITEM
import com.llw.goodnews.ui.pages.PageConstant.HOME_ITEM


/**
 * 主页面
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePage() {
    val navController = rememberAnimatedNavController()
    Scaffold(
        topBar = {
            //顶部应用栏
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSecondary,
                        overflow = TextOverflow.Ellipsis, //超出省略
                        maxLines = 1, //单行显示
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
            bottomBar = {
            BottomBarView(navController)
        }
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = HOME_ITEM,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(500)
                )
            },
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
            composable(HOME_ITEM) {
                HomeItem()
            }
            composable(COLLECTION_ITEM) {
                CollectionItem()
            }
        }
    }
}
