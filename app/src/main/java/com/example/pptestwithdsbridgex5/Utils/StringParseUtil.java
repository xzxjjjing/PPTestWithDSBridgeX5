package com.example.pptestwithdsbridgex5.Utils;

import com.example.pptestwithdsbridgex5.District;
import com.example.pptestwithdsbridgex5.Office;
import com.example.pptestwithdsbridgex5.ParseString;

import java.util.Map;

/**
 * 这是一个字符串解析类，用来处理各种解析字符串的个性化需求
 */
public class StringParseUtil {
    /**
     * 处理用 \t 分隔的一行字符串并写到javabean中
     * 疑问：
     * @param line
     */
    public static void handleLine(String line, String regex) {

        // 获得一个长度为5的String数组
        String[] splitResult = line.split(regex);
    
        // 在Map<String, District>中按键(districtNumber)搜索
        District currentDistrict;
        if ((currentDistrict = ParseString.districtMap.get(splitResult[0])) == null) {
            //put一个新的district到 Map<String, District>
            currentDistrict = CreateJavaBeanUtil.createDistrict(splitResult[0], splitResult[1],
                splitResult[2], splitResult[3], splitResult[4]);
            ParseString.districtMap.put(splitResult[0], currentDistrict);
        
        } else {
            // 在Map<String, Office>中按键(officeNumber)搜索
            Map<String, Office> currentOfficeMap = currentDistrict.getOfficeMap();
            Office currentOffice;
            if ((currentOffice = currentOfficeMap.get(splitResult[1])) == null) {
                // put一个新的office到 Map<String, Office>
                currentOffice = CreateJavaBeanUtil.createOffice(splitResult[1], splitResult[2], splitResult[3], splitResult[4]);
                currentOfficeMap.put(splitResult[1], currentOffice);
            } else {
                // add一个新的Community
                currentOffice.getCommunityMap().put(splitResult[3], CreateJavaBeanUtil.createCommunity(splitResult[3], splitResult[4]));
            }
        }
    }
}
