package cn.cnlee.demo.constrainlayout;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebviewActivity extends AppCompatActivity {

    private static final String TAG = WebviewActivity.class.getSimpleName();

    @BindView(R.id.wv)
    WebView webView;

    @BindView(R.id.loading)
    TextView loadingTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted. url: " + url);
                loadingTv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished. url: " + url);
                loadingTv.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading. url: " + request.getUrl());
                if (request.getUrl().toString().contains("baidu.com")){
                    return false;
                }
                return true;
//                return super.shouldOverrideUrlLoading(view, request);
//                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d(TAG, "onReceivedError. err: " + error.getDescription()  + " | " + error.getErrorCode());
                Log.d(TAG, "isForMainFrame: " + request.isForMainFrame());
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d(TAG, "onReceivedSslError");
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Log.d(TAG, "onReceivedHttpError");
                Log.d(TAG, "onReceivedHttpError. err: " + errorResponse.getStatusCode());
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d(TAG, "onReceivedTitle. title: " + title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG, "onProgressChanged. progress: " + newProgress);
            }
        });
//        webView.getSettings().setAllowContentAccess(true);
//        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl("https://m.baidu.com/");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}