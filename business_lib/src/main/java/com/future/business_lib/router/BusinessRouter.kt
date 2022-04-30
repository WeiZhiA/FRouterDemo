package com.future.business_lib.router

import com.future.base_lib.router.IBusinessRouter
import com.future.frouter.annotation.FRounter

@FRounter("business")
class BusinessRouter: IBusinessRouter {
    override fun test(): String {
        return "BusinessRouter"
    }
}