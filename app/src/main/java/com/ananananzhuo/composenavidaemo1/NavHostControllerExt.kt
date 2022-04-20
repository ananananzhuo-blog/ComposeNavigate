package com.ananananzhuo.composenavidaemo1

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController

/**
 * author  :mayong
 * function:
 * date    :2022/4/7
 **/



/**
 * 返回指定的route并回调参数
 */
fun NavHostController.goBackRouteWithParams(
    route: String,
    autoPop: Boolean = true,
    callback: (Bundle.() -> Unit)? = null,
) {
    val entry = getBackStackEntry(route)
    if (entry.arguments == null){
        entry.arguments = Bundle()
    }
    entry.arguments?.let {
        callback?.invoke(it)
    }
    if (autoPop) {
        popBackStack()
    }
}

/**
 * 回到上级页面，并回调参数
 */
fun NavHostController.goBackWithParams(
    autoPop: Boolean = true,
    callback: (Bundle.() -> Unit)? = null,
) {
    if (previousBackStackEntry?.arguments == null){
        previousBackStackEntry?.arguments = Bundle()
    }
    previousBackStackEntry?.arguments?.let {
        callback?.invoke(it)
    }
    if (autoPop) {
        popBackStack()
    }
}





/**
 * 返回指定的route并回调参数
 */
fun NavHostController.goBackRouteWithParams1(
    route: String,
    autoPop: Boolean = true,
    callback: (SavedStateHandle.() -> Unit)? = null,
) {
    val entry = getBackStackEntry(route)

    entry.savedStateHandle?.let {
        callback?.invoke(it)
    }
    if (autoPop) {
        popBackStack()
    }
}

/**
 * 回到上级页面，并回调参数
 */
fun NavHostController.goBackWithParams1(
    autoPop: Boolean = true,
    callback: (SavedStateHandle.() -> Unit)? = null,
) {
//    if (previousBackStackEntry?.arguments == null){
//        previousBackStackEntry?.arguments = Bundle()
//    }
    previousBackStackEntry?.savedStateHandle?.let {
        callback?.invoke(it)
    }
    if (autoPop) {
        popBackStack()
    }
}