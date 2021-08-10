package com.sunday.leetcode

import android.content.res.Resources
import android.graphics.Path
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
