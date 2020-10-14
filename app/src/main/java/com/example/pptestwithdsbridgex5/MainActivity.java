package com.example.pptestwithdsbridgex5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Toast;

import java.util.Locale;

import wendu.dsbridge.DWebView;

public class MainActivity extends AppCompatActivity {
    
    private DWebView dWebView;
    private TextToSpeech textToSpeech;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化组件
        initView();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        if (textToSpeech != null) {
            textToSpeech.stop();// 不管是否正在朗读TTS都被打断
            textToSpeech.shutdown();// 关闭，释放资源
        }
    }
    
    /**
     * 初始化组件，配置
     */
    private void initView() {
        dWebView = (DWebView) findViewById(R.id.dwv);
        dWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        
        // DSWebView返回的是x5的WebSettings
        com.tencent.smtt.sdk.WebSettings webSettings = dWebView.getSettings();
        // 支持缩放
        webSettings.setSupportZoom(true);
        // 缩放，会有放大缩小的按钮
        webSettings.setBuiltInZoomControls(true);
        // 允许webview访问内容url
        webSettings.setAllowContentAccess(true);
        // 允许对文件系统的访问（不包括对assets和res的权限）
        webSettings.setAllowFileAccess(true);
        // LOAD_NO_CACHE不允许使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 允许执行JavaScript脚本，x5内核的情况下不需要设置
        webSettings.setJavaScriptEnabled(true);
        // 开启调试模式，弹窗提示错误
        DWebView.setWebContentsDebuggingEnabled(true);
        // 初始化TTS
        initTTS();
        /**
         * DSBridge下的WebView方法-addJavascriptObject()：将提供的js处理类注入当前webview
         * 原生是：addJavascriptInterface()
         */
        if (textToSpeech != null)
            dWebView.addJavascriptObject(new JsApi(MainActivity.this, textToSpeech), null);
        
        
        dWebView.loadUrl("file:///android_asset/index.html");
        
    }
    
    /**
     * 初始化TTS
     */
    private void initTTS() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int language = textToSpeech.setLanguage(Locale.CHINA);
                    if (language == textToSpeech.LANG_NOT_SUPPORTED || language == textToSpeech.LANG_MISSING_DATA) {
                        Toast.makeText(MainActivity.this, "数据丢失 or 语言不支持", Toast.LENGTH_SHORT).show();
                    } else {
                        if (textToSpeech != null) {
                            // 设置音调,值越大声音越尖（女生），值越小则变成男声,1.0是常规
                            textToSpeech.setPitch(1.5f);
                            // 设定语速,默认1.0正常语速
                            textToSpeech.setSpeechRate(1.5f);
                        }
                    }
                }
            }
        });
    }
    
    
}