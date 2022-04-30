package com.future.test_java_lib

import com.future.frouter.FRouterUtil
import com.future.test_java_lib.router.kotlin.ITestRouter
import com.snaky.frouter.data.IModuleRouter
import kotlin.jvm.JvmStatic

object MyClass {
    @JvmStatic
    fun main(args: Array<String>) {
        FRouterUtil.getRouter(ITestRouter::class)?.test()

//        val quilName = "com.future.test_java_lib.router.kotlin.ITestRouter_FRouter"
//        val router = (Class.forName(quilName).newInstance() as IModuleRouter).getIModuleRouter().router
//        (router as ITestRouter).test()

//            .getIModuleRouter().router
    }
}