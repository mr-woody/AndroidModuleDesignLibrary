package com.woody.module1.model;//

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by yxy on 17/5/23.
 * 与服务器返回的json对应
 */

public class ReportJsonData {
    private String kid; // 知识点id
    private int ktype; // 知识点类型
    private int klevel; // 知识点层级
    private String kname;//"知识点1",
    private float degreef;//"B",
    private float predegreef;//"A",
    private String degree;//"B",
    private String predegree;//"A",
    public float degreepercent;// 0 -- 0.9999,
    public float predegreepercent;// 0 -- 0.9999,


    private boolean isOnDesk;
    /**
     * 0不在魔镜，1在魔镜 ，2我学会了
     */
    private int magicLearnTag;

    /**
     * 是否在学习范围1：在0：不在
     */
    public int kstate;

    /**
     * 主k id
     */
    public String mkid = "";
    public String mkname = "";
    /**
     * 1:未收藏，不在魔镜（未学会） 2:未收藏，在魔镜（未学会） 3:未收藏，不在魔镜（已学会）
     * 4:已收藏，不在魔镜（未学会） 5:已收藏，在魔镜（未学会） 6:已收藏，不在魔镜（已学会）
     */
    public int mlearnstate;


    public List<RelatedK> rlist;

    public static class RelatedK implements Parcelable {
        public String kid = "";
        public String kname = "";
        /**
         * 1:未收藏，不在魔镜（未学会） 2:未收藏，在魔镜（未学会） 3:未收藏，不在魔镜（已学会）
         * 4:已收藏，不在魔镜（未学会） 5:已收藏，在魔镜（未学会） 6:已收藏，不在魔镜（已学会）
         */
        public int learnstate;

        public RelatedK(){}

        protected RelatedK(Parcel in) {
            kid = in.readString();
            kname = in.readString();
            learnstate = in.readInt();
        }

        public static final Creator<RelatedK> CREATOR = new Creator<RelatedK>() {
            @Override
            public RelatedK createFromParcel(Parcel in) {
                return new RelatedK(in);
            }

            @Override
            public RelatedK[] newArray(int size) {
                return new RelatedK[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(kid);
            dest.writeString(kname);
            dest.writeInt(learnstate);
        }
    }


    public int getMagicLearnTag() {
        return magicLearnTag;
    }

    public void setMagicLearnTag(int magicLearnTag) {
        this.magicLearnTag = magicLearnTag;
    }

    private List<ReportTypeJsonData> list;

    public boolean isOnDesk() {
        return isOnDesk;
    }

    public void setOnDesk(boolean onDesk) {
        isOnDesk = onDesk;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPredegree() {
        return predegree;
    }

    public void setPredegree(String predegree) {
        this.predegree = predegree;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public float getDegreef() {
        return degreef;
    }

    public void setDegreef(float degreef) {
        this.degreef = degreef;
    }

    public float getPredegreef() {
        return predegreef;
    }

    public void setPredegreef(float predegreef) {
        this.predegreef = predegreef;
    }

    public List<ReportTypeJsonData> getList() {
        return list;
    }

    public void setList(List<ReportTypeJsonData> list) {
        this.list = list;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public int getKtype() {
        return ktype;
    }

    public void setKtype(int ktype) {
        this.ktype = ktype;
    }

    public int getKlevel() {
        return klevel;
    }

    public void setKlevel(int klevel) {
        this.klevel = klevel;
    }
}
