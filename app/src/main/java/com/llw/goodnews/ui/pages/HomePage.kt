package com.llw.goodnews.ui.pages

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.llw.goodnews.R
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.llw.goodnews.ui.BottomBarView
import com.llw.goodnews.ui.pages.PageConstant.COLLECTION_ITEM
import com.llw.goodnews.ui.pages.PageConstant.HOME_ITEM
import com.llw.goodnews.utils.showToast
import com.llw.goodnews.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


/**
 * 主页面
 */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomePage(mNavController: NavHostController, homeViewModel: HomeViewModel) {
    val navController = rememberAnimatedNavController()

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            //顶部应用栏
            val drawerState = scaffoldState.drawerState
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
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        mNavController.navigate(PageConstant.EPIDEMIC_NEWS_LIST_PAGE)
                    }) {
                        Icon(Icons.Default.Sick, contentDescription = "疫情")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        drawerContent = { DrawerView() },
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
                HomeItem(mNavController, homeViewModel)
            }
            composable(COLLECTION_ITEM) {
                CollectionItem()
            }
        }
    }
}
