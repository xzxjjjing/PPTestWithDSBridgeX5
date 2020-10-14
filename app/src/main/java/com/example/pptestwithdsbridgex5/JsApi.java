package com.example.pptestwithdsbridgex5;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.example.pptestwithdsbridgex5.Utils.TextFileToSpeechUtil;

import org.json.JSONException;
import org.json.JSONObject;

import wendu.dsbridge.CompletionHandler;

public class JsApi {
    
    private Context context;
    private TextToSpeech textToSpeech;
    
    public JsApi(Context context, TextToSpeech textToSpeech) {
        this.context = context;
        this.textToSpeech = textToSpeech;
    }
    
    //同步
    @JavascriptInterface
    public String testSyn(Object msg){
        return msg + " [syn call] ";
    }
    //异步
    @JavascriptInterface
    public void testAsyn(Object msg, CompletionHandler<String> handler) {
        handler.complete(msg + " [asyn call] ");
    }
    
    /**
     * 异步，将字符串文件解析为json文件
     * @param msg
     * @param handler
     */
    @JavascriptInterface
    public void parseDataToJson(Object msg, CompletionHandler<String> handler) {
        // 解析字符串
        ParseString parseString = new ParseString(context);
        parseString.parseToJson();
    
        try {
            //测试：构造json数据返回一系列参数，看是否能收到
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", "success");
            jsonObject.put("name", "xzx");
            jsonObject.put("age", "233");
            jsonObject.put("height", "175");
            // 回传给js
            handler.complete(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 异步，将文字转换成语音
     * @param msg
     * @param handler
     */
    @JavascriptInterface
    public void convertTextToSpeech(Object msg, CompletionHandler<String> handler) {
        try {
            JSONObject jsontemp = new JSONObject(msg.toString());
            String command = jsontemp.getString("param");// 获取命令readTextOneLine, readTextInFile
            System.out.println("command:" + command);
        
            if (command.equals("readTextOneLine")) {
                if (textToSpeech != null) {
                    // 暂时没有指定的line，先写死。以后可以通过json传过来
                    textToSpeech.speak("文字转语音模块已经启动", TextToSpeech.QUEUE_FLUSH, null);
                    // 回传给js
                    handler.complete("开始播放一行文字");
                }
            }
            if (command.equals("readTextInFile")) {
                if (textToSpeech != null) {
                    // 如果要播放文件，可能特定文件得有特定的command，现在先写死
                    // 通过写的工具类，从指定路径获取数据
                    String textInFile = TextFileToSpeechUtil.assetsFileString(context, "testFile.txt");
//                            String textInFile = TextFileToSpeechUtil.assetsFileStringPROBLEM(MainActivity.this, "testFile.txt");
                    Log.i("JsApi", textInFile);
                    if (!textInFile.equals("DATA_LOST")) {
                        textToSpeech.speak(textInFile, TextToSpeech.QUEUE_FLUSH, null);
                        // 回传给js
                        handler.complete("开始播放文本文件");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 异步，获取一些信息
     * @param msg
     * @param handler
     */
    @JavascriptInterface
    public void getInfo(Object msg, CompletionHandler<String> handler) {
        
        
        
        handler.complete("");
    }
    
}
