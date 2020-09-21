package com.experiment.npe.entity;

import java.util.List;

/**
 * 收藏类
 * Created by lbavsc on 20-9-18
 */
public class FavoritesEntity {

    /**
     * code : 1001
     * msg : 获取成功
     * data : [{"jokeId":"2048345","userId":"000002","title":"测试","coverImg":"img/joke/000002_1600582443704.jpg","postTime":"Sep 20, 2020 2:14:07 PM","content":"测试","source":"NPE","assortId":1,"collete":false},{"jokeId":"2066191","userId":"000002","title":"测试","coverImg":"img/joke/000002_1600572637717.jpg","postTime":"Sep 20, 2020 11:30:41 AM","content":"测试","source":"NPE","assortId":1,"collete":false}]
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
         * jokeId : 2048345
         * userId : 000002
         * title : 测试
         * coverImg : img/joke/000002_1600582443704.jpg
         * postTime : Sep 20, 2020 2:14:07 PM
         * content : 测试
         * source : NPE
         * assortId : 1
         * collete : false
         */

        private String jokeId;
        private String userId;
        private String title;
        private String coverImg;
        private String postTime;
        private String content;
        private String source;
        private int assortId;
        private boolean collete;

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

        public boolean isCollete() {
            return collete;
        }

        public void setCollete(boolean collete) {
            this.collete = collete;
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
                    ", collete=" + collete +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FavoritesEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
