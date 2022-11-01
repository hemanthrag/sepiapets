package com.sepia.pets.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sepia.pets.R
import com.sepia.pets.databinding.PetDetailInfoActivityBinding

class PetDetailInfoActivity :AppCompatActivity() {
    var binding:PetDetailInfoActivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.pet_detail_info_activity)

        binding!!.webView.settings.javaScriptEnabled = true

        binding!!.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding!!.progressBar.show()
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding!!.progressBar.hide()
                super.onPageFinished(view, url)
            }
        }
        binding!!.webView.loadUrl(intent.getStringExtra("contentUrl")!!)
    }
}