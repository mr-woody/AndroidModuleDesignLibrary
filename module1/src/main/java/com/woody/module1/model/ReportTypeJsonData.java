package com.woody.module1.model;//

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yxy on 17/5/23.
 * 与服务器返回的json对应，报告页，操作类型的bean
 */

public class ReportTypeJsonData implements Serializable {
    /**
     * 1：做练习
     * <p>
     * 2：看同步资源
     * <p>
     * 3：看微课
     * <p>
     * 4：做错题
     * <p>
     * 5：看拓展资源
     * <p>
     * 6：求助(.2版本新增)
     * 7：深度学习
     */
    private int type;
    /**
     * 相关别名知识点信息
     */
    private ArrayList<AliasInfo>  aliaslist;

    private boolean recommend;//"B",
    private boolean lock;//"A",
    private int helpstate;//当前 末级 知识点/章节 求助的状态：-1：原始状态 0：提交求助成功 1：已排课 2：已上课 3：已取消
    private int helpnum;//当前 末级 知识点/章节的已求助次数。用于在 ”求助状态“字段为：（-1：原始状态）或（2：已上课）或（ 3：已取消）客户端显示
    private boolean secondservice;//是否是二次服务
    private int exercisenum;//答题数（区分求助锁定状态下点击提示效果）
    private String teacherid; // 深度学习老师id

    public boolean isSecondservice() {
        return secondservice;
    }

    public void setSecondservice(boolean secondservice) {
        this.secondservice = secondservice;
    }

    public int getExercisenum() {
        return exercisenum;
    }

    public void setExercisenum(int exercisenum) {
        this.exercisenum = exercisenum;
    }

    public int getHelpstate() {
        return helpstate;
    }

    public void setHelpstate(int helpstate) {
        this.helpstate = helpstate;
    }

    public int getHelpnum() {
        return helpnum;
    }

    public void setHelpnum(int helpnum) {
        this.helpnum = helpnum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public ArrayList<AliasInfo> getAliaslist() {
        return aliaslist;
    }

    public void setAliaslist(ArrayList<AliasInfo> aliaslist) {
        this.aliaslist = aliaslist;
    }
}
