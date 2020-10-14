package com.example.pptestwithdsbridgex5.Utils;

import com.example.pptestwithdsbridgex5.Community;
import com.example.pptestwithdsbridgex5.District;
import com.example.pptestwithdsbridgex5.Office;

import java.util.HashMap;
import java.util.Map;

public class CreateJavaBeanUtil {
    
    /**
     * 创建一个Community对象
     * @param communityNumber
     * @param communityName
     * @return
     */
    public static Community createCommunity(String communityNumber, String communityName) {
        Community community = new Community();
        if(communityNumber!=null){
            community.setCommunityNumber(communityNumber);
        }
        if(communityName!=null){
            community.setCommunityName(communityName);
        }
        return community;
    }
    
    /**
     * 创建一个Office对象
     * @param officeNumber
     * @param officeName
     * @param communityMap
     * @return
     */
    public static Office createOffice(String officeNumber, String officeName, Map<String, Community> communityMap){
        Office office1 = new Office();
        if(officeNumber!=null){
            office1.setOfficeNumber(officeNumber);
        }
        if(officeName!=null){
            office1.setOfficeName(officeName);
        }
        if(communityMap!=null){
            office1.setCommunityMap(communityMap);
        }
        return office1;
    }
    
    /**
     * 创建一个Office对象与一个Community对象，并且将Community对象包装进Office对象
     * @param officeNumber
     * @param officeName
     * @param communityNumber
     * @param communityName
     * @return
     */
    public static Office createOffice(String officeNumber, String officeName, String communityNumber, String communityName){
        Office office2 = createOffice(officeNumber, officeName, new HashMap<String, Community>());
        office2.getCommunityMap().put(communityNumber, createCommunity(communityNumber, communityName));
        return office2;
    }
    
    /**
     * 创建一个District对象
     * @param districtNumber
     * @param officeMap
     * @return
     */
    public static District createDistrict(String districtNumber, Map<String ,Office> officeMap){
        District district1 = new District();
        if(districtNumber!=null){
            district1.setDistrictNumber(districtNumber);
        }
        if(officeMap!=null){
            district1.setOfficeMap(officeMap);
        }
        return district1;
    }
    
    /**
     * 创建一个District对象、一个Office对象、一个Community对象，并且层层包装好
     * @param districtNumber
     * @param officeNumber
     * @param officeName
     * @param communityNumber
     * @param communityName
     * @return
     */
    public static District createDistrict(String districtNumber, String officeNumber, String officeName,
        String communityNumber, String communityName){
        District district2 = createDistrict(districtNumber, new HashMap<String, Office>());
        district2.getOfficeMap().put(officeNumber, createOffice(officeNumber, officeName, communityNumber, communityName));
        return district2;
    }

}
