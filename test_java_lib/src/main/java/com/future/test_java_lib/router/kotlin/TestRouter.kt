package com.future.test_java_lib.router.kotlin

import com.future.frouter.annotation.FRounter
import com.future.test_java_lib.router.kotlin.ITestRouter

@FRounter("app")
class TestRouter: ITestRouter {
    override fun test() {
        println("哈哈哈")
    }
}