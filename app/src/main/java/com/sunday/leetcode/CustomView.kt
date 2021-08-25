package com.sunday.leetcode

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by Sunday on 2021/8/5
 */
class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        thread {
            while (true) {
                val random = Random()
                val r = random.nextInt(255)
                val g = random.nextInt(255)
                val b = random.nextInt(255)
                canvas.drawColor(Color.rgb(r,g,b))
                Thread.sleep(500)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}