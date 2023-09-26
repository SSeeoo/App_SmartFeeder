package com.example.smartfeeder;

import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        WebView webView = findViewById(R.id.webview);
        webView.loadUrl("http://192.168.0.13:81/stream");
    }
}
