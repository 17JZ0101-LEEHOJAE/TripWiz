package com.jz_jec_g01.tripwiz.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private int Nationality;
    private int Age;
    private int Gender;
    private int Job;
    private String Pr;
    private String Language;
    private String Area;
    private String MailAddress;
    private String PassWord;
    private String GuidHistory;
    private boolean guideflag;
    private int UserId;
    private Bitmap UserImage;
    private List<GuideTime> guideTimes;

    public List<GuideTime> getGuideTimes() { return guideTimes; }
    public int getAge() { return Age; }
    public int getGender() { return Gender; }
    public int getJob() { return Job; }
    public int getNationality() { return Nationality; }
    public int getUserId() { return UserId; }
    public boolean isGuidflag() { return guideflag; }
    public String getArea() {return Area; }
    public String getGuidHistory() {return GuidHistory; }
    public String getLanguage() { return Language; }
    public String getMailAddress() {return MailAddress; }
    public String getName() { return name; }
    public String getPassWord() {return PassWord; }
    public Bitmap getUserImage() {return UserImage; }
    public String getPr() { return Pr; }


    public void setPr(String pr) { Pr = pr; }
    public void setUserImage(Bitmap userImage) { UserImage = userImage; }
    public void setAge(int age) {Age = age; }
    public void setArea(String area) {Area = area; }
    public void setGender(int gender) { Gender = gender; }
    public void setGuidflag(boolean guidflag) { this.guideflag = guideflag; }
    public void setGuidHistory(String guidHistory) {GuidHistory = guidHistory; }
    public void setJob(int job) { Job = job; }
    public void setLanguage(String language) { Language = language; }
    public void setMailAddress(String mailAddress) { MailAddress = mailAddress; }
    public void setName(String name) { this.name = name; }
    public void setNationality(int nationality) { Nationality = nationality; }
    public void setPassWord(String passWord) { PassWord = passWord; }
    public void setUserId(int userId) { UserId = userId; }

    public User() {
        guideTimes = new ArrayList<>();
    }
    public String getGuideTime(int weekday) {
        String ret = "";
        for (int i = 0; i <= guideTimes.size(); i++) { //ガイドタイムの数分を数える
            if(guideTimes.get(i).getWeekday() == weekday) {
                String startTime = guideTimes.get(i).getStartTime().toString();
                String endTime = guideTimes.get(i).getEndTime().toString();
                ret += startTime + "～" + endTime;
            }
        }
        return ret;
    }

}
