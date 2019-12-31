package com.woody.module1.model;

/**
 * Created by yxy on 17/6/12.
 */

public class WrongJsonData {
    private String sid;//121,
    private String name;//"数学",
    private int wrong;//345
    private String surl;//学科图片下载地址

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWrong() {

        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

}
