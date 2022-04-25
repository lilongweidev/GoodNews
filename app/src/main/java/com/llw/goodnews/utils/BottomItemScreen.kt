package com.llw.goodnews.utils


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.llw.goodnews.ui.pages.PageConstant.COLLECTION_ITEM
import com.llw.goodnews.ui.pages.PageConstant.HOME_ITEM

/**
 * 定义路线名称，底部标题和图标
 */
sealed class BottomItemScreen(val route: String, val title: String, val icon: ImageVector){
    object HOME: BottomItemScreen(HOME_ITEM,"首页", Icons.Default.Home)
    object STAR: BottomItemScreen(COLLECTION_ITEM,"收藏", Icons.Default.Favorite)
}
