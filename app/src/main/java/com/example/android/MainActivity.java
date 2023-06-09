package com.example.android;

import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Project_Color)); // color.xml에 등록된 Project_Color 색상을 가져와 사용
        }

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("http://poly0411.cafe24.com/board/boardmain.jsp")) {
                    applyCustomCssForBoardMain();
                } else if (url.equals("http://poly0411.cafe24.com")) {
                    applyCustomCssForMain();
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);

        // 쿠키 관리 설정
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        webView.loadUrl("http://poly0411.cafe24.com/");
    }

    private void applyCustomCssForBoardMain() {
        // 스타일 조정 코드
        String cssCode = "body {" +
                "  zoom: 1.0;" + // 전체 크기 조정 (0.8은 예시로 조정한 값입니다)
                "}" +
                "p, h1, h2, h3, h4, h5, h6 {" +
                "  font-size: 20px !important;" + // 글자 크기 유지
                "}";
        String jsCode = "javascript:(function() { " +
                "  var style = document.createElement('style');" +
                "  style.innerHTML = '" + cssCode + "';" +
                "  document.head.appendChild(style);" +
                "})()";
        webView.loadUrl(jsCode);
    }

    private void applyCustomCssForMain() {
        // 스타일 조정 코드
        String cssCode = "body {" +
                "  zoom: 0.9;" + // 전체 크기 조정 (0.9은 예시로 조정한 값입니다)
                "}" +
                "p, h1, h2, h3, h4, h5, h6 {" +
                "  font-size: 14px !important;" + // 글자 크기 유지
                "}";
        String jsCode = "javascript:(function() { " +
                "  var style = document.createElement('style');" +
                "  style.innerHTML = '" + cssCode + "';" +
                "  document.head.appendChild(style);" +
                "})()";
        webView.loadUrl(jsCode);
    }
}
