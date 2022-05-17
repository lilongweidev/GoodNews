package com.llw.goodnews.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.llw.goodnews.viewmodel.HomeViewModel
import com.llw.goodnews.R

/**
 * 收藏
 * @author llw
 */
@Composable
fun CollectionItem(mNavController: NavHostController, viewModel: HomeViewModel) {
    viewModel.resultCollectionNews.observeAsState().value?.let {
        it.getOrNull()?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "有数据", color = colorResource(id = R.color.gray))
            }
        }
        it.getOrElse {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.HourglassEmpty,
                    contentDescription = "收藏",
                    tint = colorResource(id = R.color.gray),
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(text = "空空如也", color = colorResource(id = R.color.gray))
            }
        }
    }
}