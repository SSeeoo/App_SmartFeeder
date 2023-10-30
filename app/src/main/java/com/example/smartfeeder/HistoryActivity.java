package com.example.smartfeeder;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        WebView historyWebView = findViewById(R.id.historyWebView);

        // WebView 설정
        WebSettings webSettings = historyWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);  // 웹 페이지의 JavaScript를 활성화합니다.

        // WebViewClient 설정
        historyWebView.setWebViewClient(new WebViewClient());

        // 웹 페이지 로드
        historyWebView.loadUrl("http://ec2-54-180-120-1.ap-northeast-2.compute.amazonaws.com:5000/detection_log");
    }
}
