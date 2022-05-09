package com.llw.goodnews.ui.pages

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.llw.goodnews.R
import com.llw.goodnews.ui.theme.GoodNewsTheme

/**
 * 抽屉布局视图
 * @author llw
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DrawerView() {
    val context = LocalContext.current

    var mCameraUri: Uri? = null

    val imageUir = remember {
        mutableStateOf<Uri?>(null)
    }

    //TakePicture 调用相机，拍照后将图片保存到开发者指定的Uri，返回true
    val openCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { if (it) imageUir.value = mCameraUri })

    // 定义 Permission State
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)

    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = { /*TODO*/ },
        permissionNotAvailableContent = { /*TODO*/ }) {
        //调用权限获取之后功能
        //"可以打开相机".showToast()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
            .padding(0.dp, 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUir.value)
                .error(R.mipmap.ic_logo)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.mipmap.ic_logo),
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable {
                    if (permissionState.hasPermission) {
                        //构建Uir
                        mCameraUri = context.contentResolver.insert(
                            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI else
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI, ContentValues()
                        )
                        //启动拍照
                        openCameraLauncher.launch(mCameraUri)
                    } else {
                        //请求权限
                        permissionState.launchPermissionRequest()
                    }
                },
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "初学者-Study",
            color = colorResource(id = R.color.black),
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Android | Kotlin | Compose",
            color = colorResource(id = R.color.black),
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            ItemView("文章", 188, Modifier.weight(1f))
            ItemView("点赞", 2109, Modifier.weight(1f))
            ItemView("评论", 2897, Modifier.weight(1f))
            ItemView("收藏", 6450, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            color = colorResource(id = R.color.gray_black),
            thickness = 1.dp,//线的高度
        )
        ItemViewOnClick("CSDN主页", "https://llw-study.blog.csdn.net/", context)
        ItemViewOnClick("GitHub主页", "https://github.com/lilongweidev/", context)
    }
}


@Composable
fun ItemView(name: String, num: Int, modifier: Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = num.toString(), modifier = Modifier.padding(0.dp, 6.dp))
        Text(text = name)

    }
}

@Composable
fun ItemViewOnClick(name: String, url: String, context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
            .height(50.dp)
            .padding(12.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Stars, contentDescription = name)
        Text(
            text = name, modifier = Modifier
                .padding(12.dp, 0.dp)
                .weight(1f)
        )
        Icon(Icons.Default.ChevronRight, contentDescription = "打开")
    }
}


@Preview
@Composable
fun DefaultPreview() {
    GoodNewsTheme {
        DrawerView()
    }
}
