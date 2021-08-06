package com.sunday.leetcode

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by Sunday on 2021/8/5
 */
class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }
}