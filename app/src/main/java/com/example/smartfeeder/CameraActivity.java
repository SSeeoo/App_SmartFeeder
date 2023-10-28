package com.example.smartfeeder;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 자바스크립트 사용 허용
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient()); // 경고 메시지 없이 표시
        webView.loadUrl("http://192.168.35.153:81/stream");
    }
}
