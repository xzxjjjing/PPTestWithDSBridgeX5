package com.example.pptestwithdsbridgex5;

import java.util.Map;

public class District {

    private String districtNumber;
    private Map<String ,Office> officeMap;

    public String getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(String districtNumber) {
        this.districtNumber = districtNumber;
    }

    public Map<String, Office> getOfficeMap() {
        return officeMap;
    }

    public void setOfficeMap(Map<String, Office> officeMap) {
        this.officeMap = officeMap;
    }
}
