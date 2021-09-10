package com.sunday.leetcode

import android.content.Intent
import android.content.res.Resources
import android.graphics.Path
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val socket = Socket()
        button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("test://123")
            startActivity(intent)
        }

        // 这个是拿到大小的最佳时机
        button.viewTreeObserver.addOnGlobalLayoutListener {

        }

        // 这个就是绘制完成
        button.post {

        }

        val test = Test()
        test.outPut("123", "abc")
    }

    override fun onResume() {
        super.onResume()
        thread {
            if (window.decorView.parent != null) {
                Log.d("1", "")
            }
            button.setText("123")
        }
    }
}

class Client : WebViewClient() {

}

class ChromeClient : WebChromeClient() {
    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
    }
}

val Float.px
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )
