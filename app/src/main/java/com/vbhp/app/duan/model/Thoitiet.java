package com.vbhp.app.duan.model;

public class Thoitiet {
    public String Day;
    public String Status;
    public String Image;
    public String MaxTemp;
    public String MinTemp;

    public Thoitiet(String day, String status, String image, String maxTemp, String minTemp) {
        Day = day;
        Status = status;
        Image = image;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
    }
}