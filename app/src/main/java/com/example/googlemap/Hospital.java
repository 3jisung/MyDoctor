package com.example.googlemap;

public class Hospital {
    private String address;
    private String type;
    private String simpleMap;
    private String name;
    private String mainNumber;
    private String emergencyNumber;
    //private String 영업시간s
    private Double latitude;
    private Double longitude;

    public Hospital()
    {
        address = null; type = null; simpleMap = null; name = null; mainNumber = null;
        emergencyNumber = null; longitude = 0.0; latitude = 0.0;
    }

    public String getAddress() {return address;}
    public String getType() {return type;}
    public String getSimpleMap() {return simpleMap;}
    public String getName() {return name;}
    public String getMainNumber() {return mainNumber;}
    public String getEmergencyNumber() {return emergencyNumber;}
    public Double getLatitude() {return latitude;}
    public Double getLongitude() {return longitude;}

    public void setAddress(String address) {this.address = address;}
    public void setType(String type) {this.type = type;}
    public void setSimpleMap(String simpleMap) {this.simpleMap = simpleMap;}
    public void setName(String name) {this.name = name;}
    public void setMainNumber(String mainNumber) {this.mainNumber = mainNumber;}
    public void setEmergencyNumber(String emergencyNumber) {this.emergencyNumber = emergencyNumber;}
    public void setLatitude(Double latitude) {this.latitude = latitude;}
    public void setLongitude(Double longitude) {this.longitude = longitude;}
}
