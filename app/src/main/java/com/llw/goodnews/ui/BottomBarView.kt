package com.llw.goodnews.ui

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.llw.goodnews.R
import com.llw.goodnews.ui.theme.Blue200
import com.llw.goodnews.ui.theme.Blue300
import com.llw.goodnews.utils.BottomItemScreen

/**
 * 底部导航视图
 */
@Composable
fun BottomBarView(navController: NavController) {
    val navItem = listOf(
        BottomItemScreen.HOME,
        BottomItemScreen.STAR
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    //当前路线
    val currentRoute = navBackStackEntry?.destination?.route
    BottomAppBar {
        navItem.forEach { 
            BottomNavigationItem(
                label = { Text(text = it.title) },//设置item标签
                icon = { Icon(imageVector = it.icon, contentDescription = it.title)},//设置item图标
                selectedContentColor = Color.White,//选中时颜色
                unselectedContentColor = colorResource(id = R.color.gray),//未选中时颜色
                selected = currentRoute == it.route,//选中时赋值
                onClick = {
                    //点击时根据，选中了不同items,则先赋值，在进行路线导航，导航后保存状态，
                    navController.navigate(it.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}