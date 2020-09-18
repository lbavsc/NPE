package com.experiment.npe.entity;

import java.util.List;

/**
 * Created by lbavsc on 20-9-14
 */
public class JokeEntity {


    /**
     * code : 1001
     * msg : 获取成功
     * data : [{"jokeId":"1000001","userId":"000001","title":"意大利时装界直面种族主义问题","coverImg":"img/user/ic_default.jpg","postTime":"Sep 15, 2020 10:12:38 AM","content":"意大利加勒比裔时装设计师斯泰拉·让（Stella Jean）主动退出米兰时装周，为的是推动业界打击种族主义，接纳有色人种就业。","source":"BBC","assortId":1}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * jokeId : 1000001
         * userId : 000001
         * title : 意大利时装界直面种族主义问题
         * coverImg : img/user/ic_default.jpg
         * postTime : Sep 15, 2020 10:12:38 AM
         * content : 意大利加勒比裔时装设计师斯泰拉·让（Stella Jean）主动退出米兰时装周，为的是推动业界打击种族主义，接纳有色人种就业。
         * source : BBC
         * assortId : 1
         */

        private String jokeId;
        private String userId;
        private String title;
        private String coverImg;
        private String postTime;
        private String content;
        private String source;
        private int assortId;

        public String getJokeId() {
            return jokeId;
        }

        public void setJokeId(String jokeId) {
            this.jokeId = jokeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getAssortId() {
            return assortId;
        }

        public void setAssortId(int assortId) {
            this.assortId = assortId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "jokeId='" + jokeId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", title='" + title + '\'' +
                    ", coverImg='" + coverImg + '\'' +
                    ", postTime='" + postTime + '\'' +
                    ", content='" + content + '\'' +
                    ", source='" + source + '\'' +
                    ", assortId=" + assortId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "JokeEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
