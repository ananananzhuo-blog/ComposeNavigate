

## 概览
所谓的导航就是页面跳转，因为Compose中每一个@Composable注解标注的方法就可以成为一个视图，所以导航就是用来处理这些视图之间的跳转操作。

## 配置
1. 配置我们的根gradle中kotlin插件版本

```
  classpath "com.android.tools.build:gradle:7.1.0-alpha03"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10"
```

2. 加入导航依赖

```
implementation("androidx.navigation:navigation-compose:2.4.0-alpha02")
```
3. 根gradle配置compose version

```
 compose_version = '1.0.0-beta09'
```

## 基础用法
使用导航首先需要构建一个导航图，将每个导航视图分别与一个字符串相关联，本例中我们模拟这样一个场景，点击商品列表跳转到商品详情页面
1. 构建导航图

导航图中我们构建了两个路由，goodsList代表商品列表，goodsDetail代表商品详情。
```

@Composable
fun Greeting(name: String) {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = "goodsList") {
        composable("goodsList") {
            GoodsList(controller)
        }
        composable("goodsDetail") {
            GoodsDetail()
        }
    }
}
```
2. 商品列表


```
@Composable
fun GoodsList(controller: NavHostController) {
    Column {
        (1..9).forEach {
            GoodsItem(controller, it)
        }
    }
}
```
商品item

点击商品item使用路由跳转到商品详情

```
@Composable
fun GoodsItem(controller: NavHostController, i: Int) {
    Column {
        Row(modifier = Modifier.clickable {
            controller.navigate("goodsDetail")
        }, verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.apple), contentDescription = "")
            Text(text = "第件${i}件商品", modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
        Divider(color = Color.Red)
    }
}
```
3. 商品详情

```
@Composable
fun GoodsDetail() {
    Column(modifier = Modifier.fillMaxSize(1f),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.apple), contentDescription = "",modifier = Modifier.size(200.dp))
        Text(text = "安卓机器人",style = TextStyle(fontSize = 40.sp))
    }

}
```
4. 效果图
![](https://files.mdnice.com/user/15648/f2cb2501-4259-4da0-bb1f-ea5d8c246af3.gif)


## 传递参数
我们将上面的例子进行一些改进，点击商品Item的时候将商品的id传入的详情页面展示。

直接上全部代码，如果上一节的demo仔细看了的话，那么直接看代码中的注释就ok：
1. 代码
```

@Composable
fun Greeting(name: String) {
    val controller = rememberNavController()
    NavHost(navController = controller, startDestination = "goodsList") {
        composable("goodsList") {
            GoodsList(controller)
        }
        composable("goodsDetail/{goodsId}", arguments = listOf(navArgument("goodsId") {//路由中三部分的goodsId名称必须一致
            type = NavType.StringType//表示传递的参数是String类型
        })) {
            GoodsDetail(it.arguments?.getString("goodsId"))//获取商品id传递给GoodsDetail视图
        }
    }
}

@Composable
fun GoodsList(controller: NavHostController) {
    Column {
        (1..9).forEach {
            GoodsItem(controller, it)
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
```
2. 效果

![](https://files.mdnice.com/user/15648/e52dc900-cc1b-4b6f-a4d0-858eef418188.gif)

### 传递默认参数
我们可以再构建导航路由的时候使用defaultValue添加默认参数，代码如下：

```
 composable("goodsDetail/{goodsId}", arguments = listOf(navArgument("goodsId") {//路由中三部分的goodsId名称必须一致
            type = NavType.StringType//表示传递的参数是String类型
            defaultValue="默认商品"
        })) {
            GoodsDetail(it.arguments?.getString("goodsId"))//获取商品id传递给GoodsDetail视图
        }
```

