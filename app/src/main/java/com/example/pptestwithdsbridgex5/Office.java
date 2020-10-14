package com.example.pptestwithdsbridgex5;

import java.util.Map;

public class Office {

    private String officeNumber;
    private String officeName;
    private Map<String, Community> communityMap;

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Map<String, Community> getCommunityMap() {
        return communityMap;
    }

    public void setCommunityMap(Map<String, Community> communityMap) {
        this.communityMap = communityMap;
    }
}
