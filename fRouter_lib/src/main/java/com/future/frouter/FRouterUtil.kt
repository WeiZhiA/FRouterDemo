package com.future.frouter

import com.future.frouter.processor.FRouterProcessor.Companion.CLASS_PREFIX
import com.future.frouter.data.IModuleRouter
import kotlin.reflect.KClass

object FRouterUtil {

    private val routerMap = mutableMapOf<String, Any>()

    fun <T:Any> getRouter(clazz: KClass<T>): T? {
        var router: Any?
        try {
            val qualifiedName = clazz.qualifiedName?:""
            router = routerMap[clazz.qualifiedName]
            if (router == null) {
                val qualiName = clazz.qualifiedName + CLASS_PREFIX
                val iModuleRouter = Class.forName(qualiName).getDeclaredConstructor().newInstance() as IModuleRouter

                router = iModuleRouter.getIModuleRouter().router
                routerMap[qualifiedName] = router
            }
            return router as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}