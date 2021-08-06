package com.sunday.leetcode

import android.content.res.Resources
import android.graphics.Path
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val path = Path()
        val dialogFragment = DialogFragment()
        dialogFragment.dialog.window
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100F, Resources.getSystem().displayMetrics)
    }
}


val Float.px
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )
