package com.example.pptestwithdsbridgex5;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.pptestwithdsbridgex5.Utils.StringParseUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 将数据解析为json格式的业务类
 */
public class ParseString {

    private Context context;
    public static Map<String, District> districtMap = new HashMap<>();
    
    public ParseString(Context context) {
        this.context = context;
    }
    
    /**
     * 解析数据功能的js端对应方法
     */
    public void parseToJson() {
        // first，获取数据，get from assets
        /**
         * Java 7的新特性：在try后的小括号内声明的资源，try块结束后一定会关闭，相当于起到一个finally的作用
         * 注：必须是实现了Closeable或AutoCloseable接口的类
         */
        try (InputStream inputStream = context.getResources().getAssets().open("data.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader((inputStreamReader))) {
        
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                // 1.处理每行数据，装入javabean
                StringParseUtil.handleLine(line, "\t");
            }
            // 2.将javabean通过fastjson变成json数据，随后写入文件
            convertToJson(districtMap);
            System.out.println("区号的个数：" + districtMap.size());
    
            /**
             * 在这里测试cache目录
             * 证毕，存在，内部路径
             */
//            isEmptyDirectory(context.getCacheDir().getAbsolutePath());
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * convert List<District> districts to Json
     * @param districtsMap2
     */
    public void convertToJson(Map<String, District> districtsMap2) {
        // 遍历map districts
        for (String key : districtsMap2.keySet()) {
            //districtNumber做文件名前缀
            StringBuilder districtNumber = new StringBuilder(districtsMap2.get(key).getDistrictNumber());
            // 使用fastjson转化map对象
            String jsonStr = JSON.toJSONString(districtsMap2.get(key).getOfficeMap());
            // 将转化得到的json字符串保存到文件中
            createJsonFile(districtNumber.append(".json").toString(), jsonStr);
        }
    }

    /**
     * write json into files，
     */
    public void createJsonFile(String fileName, String json) {
        // 存储路径不是/storage/emulated/0/Android/data/com.example.playmusic/cache
        // 存储路径是  /data/com.example.playmusic/cache
        // 写到cache里，这个就是缓存的目录，应用删除缓存就会消失
        // 并不需要去判断文件是否存在，如果不存在但不是无法创建就能自动被创建
        File file = new File(context.getCacheDir(), fileName);
        // 参数为false，表示覆盖，从文件开头写；true，则从文件尾部写
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
            byte[] jsonData = json.getBytes();
            fileOutputStream.write(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 功能：测试/data/data/<package name...>/cache下是否有刚才写入的文件，返回文件列表
     * 测试结果：的确存在/data/data/<package name...>/cache这样的路径，最初以为是外部存储的相对路径呢，但并不是，是应用内部存储的绝对路径
     * @param directory
     */
    public void isEmptyDirectory(String directory) {
        File file = new File(directory);
        if (file != null && file.isDirectory()) {
            File[] list = file.listFiles();
            for (File temp : list) {
                Log.i("ParseString------", temp.getName());
            }
            System.out.println("文件的个数是：：：" + list.length);
        } else {
            Log.i("ParseString------", "文件目录不存在");
        }
    }
    
}
