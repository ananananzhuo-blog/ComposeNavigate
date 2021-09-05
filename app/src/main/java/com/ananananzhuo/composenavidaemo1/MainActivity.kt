package com.ananananzhuo.composenavidaemo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ananananzhuo.composenavidaemo1.ui.theme.ComposeNaviDaemo1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNaviDaemo1Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

const val collection = "collection"
const val userCenter = "userCenter"

@Composable
fun Greeting(name: String) {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = "goodsList") {
        composable("goodsList") {
            GoodsList(controller)
        }
        composable(
            "goodsDetail/{goodsId}",
            arguments = listOf(navArgument("goodsId") {//路由中三部分的goodsId名称必须一致
                type = NavType.StringType//表示传递的参数是String类型
                defaultValue = "默认商品"
            })
        ) {
            GoodsDetail(it.arguments?.getString("goodsId"))//获取商品id传递给GoodsDetail视图
        }
        navigation(startDestination = userCenter, route = "user") {
            composable(userCenter) {
                userCenter(controller)
            }
            composable(collection) {
                collection()
            }
        }
    }
}

@Composable
fun GoodsList(controller: NavHostController) {
    Column {
        Button(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1f)
            .height(80.dp)
            .clip(RoundedCornerShape(10.dp)), onClick = {
            controller.navigate("user")
        }) {
            Text(text = "点击进入嵌套导航首页")
        }
        Column {
            (1..9).forEach {
                GoodsItem(controller, it)
            }
        }
    }
}

@Composable
fun GoodsDetail(id: String?) {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.apple),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Text(text = "安卓机器人: $id", style = TextStyle(fontSize = 40.sp))//这里将商品id展示出来
    }

}

@Composable
fun GoodsItem(controller: NavHostController, i: Int) {
    Column {
        Row(modifier = Modifier.clickable {
            controller.navigate("goodsDetail/$i")//这里将商品id拼接到参数后面
        }, verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.apple), contentDescription = "")
            Text(text = "第件${i}件商品", modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
        Divider(color = Color.Red)
    }
}

/**
 * 个人中心
 */
@Composable
fun userCenter(controller: NavHostController) {
    Column(
        Modifier
            .fillMaxSize(1f)
            .clickable {
                controller.navigate(collection)
            }) {
        Text(text = "用户中心点击进入收藏")
    }
}


@Composable
fun collection() {
    Column {
        Text(text = "收藏")
    }
}