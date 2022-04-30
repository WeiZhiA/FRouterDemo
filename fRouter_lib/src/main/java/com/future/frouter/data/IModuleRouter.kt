package com.snaky.frouter.data

import java.util.*
import kotlin.reflect.KClass

interface IModuleRouter {
//    fun getModule(): String  /** 所在的模块名 **/
//    fun getRouterClass(): Class<Any> /** 实现的 **/
//    fun getInterfaceClass(): Class<Any> /** 父接口 **/

    fun getIModuleRouter(): RouterData


}

data class RouterData(
    val moduleName: String, //所在的模块
    val router: Any,
    val interfaceQueName: String
)