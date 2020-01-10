package com.jz_jec_g01.tripwiz.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int userId;
    private String name;
    private String age;
    private int gender;
    private String job;
    private String nationality;
    private String introduction;
    private int guideStatus;
    private int price;
    private List<String> areaList = new ArrayList<>();
    private List<String> weekList = new ArrayList<>();
    private String profile;

    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getAge() { return age; }
    public int getGender() { return gender; }
    public String getJob() { return job; }
    public String getNationality() { return nationality; }
    public String getIntroduction() { return introduction; }
    public int getGuideStatus() { return guideStatus; }
    public int getPrice() { return price; }
    public String getProfile() {return profile; }

    public List<String> getAreaList() { return areaList; }
    public List<String> getWeekList() { return weekList; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setAge(String age) { this.age = age; }
    public void setGender(int gender) { this.gender = gender; }
    public void setJob(String job) { this.job = job; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public void setGuideStatus(int guideStatus) { this.guideStatus = guideStatus; }
    public void setPrice(int price) { this.price = price; }
    public void setProfile(String profile) {this.profile = profile; }

    public void setAreaList(List<String> areaList) { this.areaList = areaList; }
    public void setWeekList(List<String> weekList) { this.weekList = weekList; }
}
