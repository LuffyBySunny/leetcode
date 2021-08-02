package com.sunday.leetcode

import android.content.res.Resources
import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val path = Path()

        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100F, Resources.getSystem().displayMetrics)
    }
}


val Float.px
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )
