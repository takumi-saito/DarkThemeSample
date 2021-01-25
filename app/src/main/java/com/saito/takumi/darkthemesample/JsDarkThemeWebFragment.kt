package com.saito.takumi.darkthemesample

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.webkit.WebViewFeature


class JsDarkThemeWebFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_js_dark_theme_web, container, false)
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = view.findViewById<WebView>(R.id.web_view)
        // WebView呼び出し
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/js_dark.html")
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(MyJavaScript(requireContext()), "android")
        webView.setBackgroundColor(Color.TRANSPARENT) // ダークモードのloadで白のチラつきを回避
    }

}
class MyJavaScript(private val context: Context) {
    @JavascriptInterface
    fun isNightMode(): Boolean {
        var nightMode = false
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            nightMode = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        }
        return nightMode
    }
}