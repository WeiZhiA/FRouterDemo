package com.future.frouterdemo.router.java;

import com.future.frouter.annotation.FRounter;
import com.future.frouterdemo.router.kotlin.ITestRouter;

@FRounter(module = "哈哈哈")
public class TestRouter implements ITestRouter {
    @Override
    public String test() {
        System.out.printf("测试啊");
        return "哈哈哈";
    }

}
