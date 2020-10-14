package com.example.pptestwithdsbridgex5.Utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 这是一个读取各类型文本文件，随后拼接成字符串提供给TextToSpeech转成语音的工具类
 */
public class TextFileToSpeechUtil {
    
    /**
     * 从assets中读取指定文件（经过测试，虽然可以拼接字符串，但是返回的字符串却无法被TTS读取，可能是异步同步的问题吧）
     * @param context
     * @return
     */
    public static String assetsFileString(Context context, String filename) {
    
        try (InputStream inputStream = context.getResources().getAssets().open(filename);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader((inputStreamReader))) {
        
            StringBuilder result = new StringBuilder();
            String line;
            /**
             * 用StringBuilder来代替String获取每次的line，然后添加到result里，再清空line
             * 这么做是为了减小用String的空间开销
             */
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            Log.i("TextFileToSpeechUtil------字符串是：", result.toString());
            return result.toString();
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //资源虽然一定会自动关闭，但是返回值不会，所以要有finally语句
            return "DATA_LOST";
        }
    }
    /**
     * 有问题的版本
     * 用了StringBuilder的line有大问题
     */
    public static String assetsFileStringPROBLEM(Context context, String filename) {
    
        try (InputStream inputStream = context.getResources().getAssets().open(filename);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader((inputStreamReader))) {
        
            StringBuilder result = new StringBuilder();
            StringBuilder line = new StringBuilder();
            /**
             * 用StringBuilder来代替String获取每次的line，然后添加到result里，再清空line
             * 这么做是为了减小用String的空间开销
             */
            while ((line.append(bufferedReader.readLine()).toString()) != null) {
                result.append(line);
                line.setLength(0);//清空line
            }
            Log.i("TextFileToSpeechUtil------字符串是：", result.toString());
            return result.toString();
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //资源虽然一定会自动关闭，但是返回值不会，所以要有finally语句
            return "DATA_LOST";
        }
    }
    
    
    
    /**
     * 从别的目录读取指定文件
     */
    
}
