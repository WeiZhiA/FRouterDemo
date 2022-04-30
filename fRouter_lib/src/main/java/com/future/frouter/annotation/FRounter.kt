package com.future.frouter.annotation


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class FRounter (
    val module: String = ""
)