package com.experiment.npe.entity;

import java.util.List;

public class JokeDetailsEntity {

    /**
     * code : 1001
     * msg : 获取成功
     * data : {"jokeId":"1009115","userId":"000002","title":"测试","coverImg":"img/joke/000002_1600499783572.jpg","postTime":"Sep 19, 2020 3:16:32 PM","content":"测试","source":"NPE","assortId":1,"remarks":[]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * jokeId : 1009115
         * userId : 000002
         * title : 测试
         * coverImg : img/joke/000002_1600499783572.jpg
         * postTime : Sep 19, 2020 3:16:32 PM
         * content : 测试
         * source : NPE
         * assortId : 1
         * remarks : []
         */

        private String jokeId;
        private String userId;
        private String title;
        private String coverImg;
        private String postTime;
        private String content;
        private String source;
        private int assortId;
        private List<?> remarks;

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

        public List<?> getRemarks() {
            return remarks;
        }

        public void setRemarks(List<?> remarks) {
            this.remarks = remarks;
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
                    ", remarks=" + remarks +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "JokeDetailsEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
