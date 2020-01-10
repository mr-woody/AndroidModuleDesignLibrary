package com.woody.module1.model.classrom;

import java.util.List;

/**
 * Created by panweijie on 2017/3/6.
 */

public class InclassRoomDto {


    /**
     * content : [{"data":{"roomid":330460,"scolor":"#FFFFA726","publishtime":1490000335000,"classname":"九年级-18班","teacherid":61110317,"subjectname":"语文","iconurls":["http://hd.okjiaoyu.cn/hd_JlBKACMVvW.png"],"courseid":330460,"subjectid":1,"teachername":"石焕焕","classid":1974,"coursename":"测试003"},"createTime":1490001827111,"roomId":330460},{"data":{"roomid":322828,"scolor":"#FFFFA726","publishtime":1488796505000,"classname":"九年级-18班","teacherid":61110317,"subjectname":"语文","iconurls":["http://hd.okjiaoyu.cn/hd_JlBKACMVvW.png"],"courseid":322828,"subjectid":1,"teachername":"石焕焕","classid":1974,"coursename":"66"},"createTime":1489999008839,"roomId":322828},{"data":{"roomid":330402,"scolor":"#FFFFA726","publishtime":1489992817000,"classname":"九年级-18班","teacherid":61110317,"subjectname":"语文","iconurls":["http://hd.okjiaoyu.cn/hd_JlBKACMVvW.png"],"courseid":330402,"subjectid":1,"teachername":"石焕焕","classid":1974,"coursename":"0320--测试"},"createTime":1489997872542,"roomId":330402},{"data":{"roomid":330373,"scolor":"#FFFFA726","publishtime":1489748763000,"classname":"九年级-18班","teacherid":61110317,"subjectname":"语文","iconurls":["http://hd.okjiaoyu.cn/hd_JlBKACMVvW.png"],"courseid":330373,"subjectid":1,"teachername":"石焕焕","classid":1974,"coursename":"11111"},"createTime":1489996550718,"roomId":330373}]
     * errorCode : 0
     * code : 0
     * msg : 获取房间列表成功
     * success : true
     */

    private int errorCode;
    private int code;
    private String msg;
    private boolean success;
    private List<ContentBean> content;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * data : {"roomid":330460,"scolor":"#FFFFA726","publishtime":1490000335000,"classname":"九年级-18班","teacherid":61110317,"subjectname":"语文","iconurls":["http://hd.okjiaoyu.cn/hd_JlBKACMVvW.png"],"courseid":330460,"subjectid":1,"teachername":"石焕焕","classid":1974,"coursename":"测试003"}
         * createTime : 1490001827111
         * roomId : 330460
         */

        private DataBean data;
        private long createTime;
        private long roomId;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
        }

        public static class DataBean {
            /**
             * roomid : 330460
             * scolor : #FFFFA726
             * publishtime : 1490000335000
             * classname : 九年级-18班
             * teacherid : 61110317
             * subjectname : 语文
             * iconurls : ["http://hd.okjiaoyu.cn/hd_JlBKACMVvW.png"]
             * courseid : 330460
             * subjectid : 1
             * teachername : 石焕焕
             * classid : 1974
             * coursename : 测试003
             */

            private long roomid;
            private String scolor;
            private long publishtime;
            private String classname;
            private long teacherid;
            private String subjectname;
            private long courseid;
            private long subjectid;
            private String teachername;
            private long classid;
            private String coursename;
            private List<String> iconurls;

            //同步课堂新增
            private boolean islive;
            private long livestarttime;
            private long liveendtime;

            public long getRoomid() {
                return roomid;
            }

            public void setRoomid(long roomid) {
                this.roomid = roomid;
            }

            public String getScolor() {
                return scolor;
            }

            public void setScolor(String scolor) {
                this.scolor = scolor;
            }

            public long getPublishtime() {
                return publishtime;
            }

            public void setPublishtime(long publishtime) {
                this.publishtime = publishtime;
            }

            public String getClassname() {
                return classname;
            }

            public void setClassname(String classname) {
                this.classname = classname;
            }

            public long getTeacherid() {
                return teacherid;
            }

            public void setTeacherid(long teacherid) {
                this.teacherid = teacherid;
            }

            public String getSubjectname() {
                return subjectname;
            }

            public void setSubjectname(String subjectname) {
                this.subjectname = subjectname;
            }

            public long getCourseid() {
                return courseid;
            }

            public void setCourseid(long courseid) {
                this.courseid = courseid;
            }

            public long getSubjectid() {
                return subjectid;
            }

            public void setSubjectid(long subjectid) {
                this.subjectid = subjectid;
            }

            public String getTeachername() {
                return teachername;
            }

            public void setTeachername(String teachername) {
                this.teachername = teachername;
            }

            public long getClassid() {
                return classid;
            }

            public void setClassid(long classid) {
                this.classid = classid;
            }

            public String getCoursename() {
                return coursename;
            }

            public void setCoursename(String coursename) {
                this.coursename = coursename;
            }

            public List<String> getIconurls() {
                return iconurls;
            }

            public void setIconurls(List<String> iconurls) {
                this.iconurls = iconurls;
            }

            public boolean getIslive() {
                return islive;
            }

            public void setIslive(boolean islive) {
                this.islive = islive;
            }

            public long getLivestarttime() {
                return livestarttime;
            }

            public void setLivestarttime(long livestarttime) {
                this.livestarttime = livestarttime;
            }

            public long getLiveendtime() {
                return liveendtime;
            }

            public void setLiveendtime(long liveendtime) {
                this.liveendtime = liveendtime;
            }
        }
    }
}
