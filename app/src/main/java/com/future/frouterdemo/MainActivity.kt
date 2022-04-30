package com.future.frouterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.future.base_lib.router.IBusinessRouter
import com.future.frouter.FRouterUtil
import com.future.frouterdemo.router.kotlin.ITestRouter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.textView).setOnClickListener {
            val text = FRouterUtil.getRouter(IBusinessRouter::class)?.test()?:"出错了"
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()

        }

    }
}