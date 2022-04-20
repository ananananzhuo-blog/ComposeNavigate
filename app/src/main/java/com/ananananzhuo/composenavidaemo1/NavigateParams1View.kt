package com.ananananzhuo.composenavidaemo1

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ananananzhuo.composelib.logEE

/**
 * author  :mayong
 * function:
 * date    :2022/4/7
 **/
const val navigate_param_transfer1 = "navigate_param_transfer1"//导航页面双向数据传递
const val navigate_param_transfer2 = "navigate_param_transfer2"//导航页面双向数据传递

@Composable
fun NavigateParams1View(controller: NavHostController) {
    val bundle = controller.currentBackStackEntryAsState().value
    val saveStateHandle = controller.currentBackStackEntryAsState().value?.savedStateHandle
    logEE("页面重组")
    Column(Modifier
        .fillMaxSize()
        .background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "展示下一级页面返回来的数据", style = TextStyle(fontSize = 20.sp))
        Box(Modifier
            .padding(horizontal = 14.dp, vertical = 20.dp)
            .size(120.dp)
            .background(color = Color.Gray, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = bundle?.arguments?.getString("data") ?: "未返回数据")
        }
        Box(Modifier
            .padding(horizontal = 14.dp, vertical = 20.dp)
            .size(120.dp)
            .background(color = Color.Gray, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = saveStateHandle?.get<String>("data")?:"")
        }
        Button(onClick = {
            controller.navigate(navigate_param_transfer2)
        }, modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "点击跳转到下一级页面")
        }
    }
}

@Composable
fun NavigateParams2View(controller: NavHostController) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "展示下一级页面返回来的数据")
        Button(onClick = {
            controller.goBackRouteWithParams(navigate_param_transfer1) {
                putString("data",
                    "Hello world to you heihei")
            }
        },modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "点击跳转到下一级页面")
        }
        Button(onClick = {
            controller.goBackWithParams {
                putString("data", "Hello world to you")
            }
        },modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "点击跳转到下一级页面")
        }
        Button(onClick = {
            controller.goBackRouteWithParams1(navigate_param_transfer1) {
                set("data", "使用SaveStateHandler返回指定页面数据")
            }
        },modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "使用SaveStateHandler返回指定页面数据")
        }
        Button(onClick = {
            controller.goBackWithParams1 {
                set("data", "使用SaveStateHandler返回上级页面数据")
            }
        },modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "使用SaveStateHandler返回上级页面数据")
        }
    }
}


