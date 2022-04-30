package com.future.frouterdemo.router.kotlin

import com.future.frouter.annotation.FRounter

@FRounter("app")
class TestRouter: ITestRouter {
    override fun test(): String {
        println("哈哈哈")

        return "哈哈哈";
    }
}