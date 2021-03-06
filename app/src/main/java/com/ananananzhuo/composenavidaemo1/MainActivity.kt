package com.ananananzhuo.composenavidaemo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.ananananzhuo.composelib.ListView
import com.ananananzhuo.composelib.bean.ItemData
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
const val home = "home"

@Composable
fun Greeting(name: String) {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = home) {
        composable(home){
            Home(controller)
        }
        composable("goodsList") {
            GoodsList(controller)
        }
        composable(
            "goodsDetail/{goodsId}",
            arguments = listOf(navArgument("goodsId") {//?????????????????????goodsId??????????????????
                type = NavType.StringType//????????????????????????String??????
                defaultValue = "????????????"
            })
        ) {
            GoodsDetail(it.arguments?.getString("goodsId"))//????????????id?????????GoodsDetail??????
        }
        navigation(startDestination = userCenter, route = "user") {
            composable(userCenter) {
                userCenter(controller)
            }
            composable(collection) {
                collection()
            }
        }
        composable(navigate_param_transfer1) {
            NavigateParams1View(controller = controller)
        }
        composable(navigate_param_transfer2) {
            NavigateParams2View(controller = controller)
        }
    }
}
@Composable
fun Home(controller: NavHostController) {
    val state = rememberLazyListState()
    ListView(datas = mutableListOf(
        ItemData(title = "????????????", content = "", tag = "goodsList"),
        ItemData(title = "????????????", content = "", tag = "goodsDetail"),
        ItemData(title = "????????????", content = "", tag = userCenter),
        ItemData(title = "??????", content = "", tag = collection),
        ItemData(title = "???????????????????????????????????????????????????????????????", content = "", tag = navigate_param_transfer1),
        ItemData(title = "??????", content = "", tag = navigate_param_transfer2)
    ), state = state,click = { itemData: ItemData, index: Int, id: Int ->
        controller.navigate(itemData.tag)
    })
}


@Composable
fun GoodsList(controller: NavHostController) {
    Column {
        Button(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1f)
            .height(80.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Red)
            .padding(10.dp), onClick = {
            controller.navigate("user")
        }) {
            Text(text = "??????????????????????????????")
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
        Text(text = "???????????????: $id", style = TextStyle(fontSize = 40.sp))//???????????????id????????????
    }

}

@Composable
fun GoodsItem(controller: NavHostController, i: Int) {
    Column {
        Row(modifier = Modifier.clickable {
            controller.navigate("goodsDetail/$i")//???????????????id?????????????????????
        }, verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.apple), contentDescription = "")
            Text(text = "??????${i}?????????", modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
        Divider(color = Color.Red)
    }
}

/**
 * ????????????
 */
@Composable
fun userCenter(controller: NavHostController) {
    Column(
        Modifier
            .fillMaxSize(1f)
            .clickable {
                controller.navigate(collection)
            }) {
        Text(text = "??????????????????????????????")
    }
}


@Composable
fun collection() {
    Column {
        Text(text = "??????")
    }
}