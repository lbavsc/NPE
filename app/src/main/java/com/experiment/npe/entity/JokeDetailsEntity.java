package com.experiment.npe.entity;

import java.util.List;

public class JokeDetailsEntity {


    /**
     * code : 1001
     * msg : 获取成功
     * data : {"jokeId":"2048345","userId":"000002","title":"测试","coverImg":"img/joke/000002_1600582443704.jpg","postTime":"Sep 20, 2020 2:14:07 PM","content":"测试","source":"NPE","assortId":1,"collete":true,"remarks":[{"remarkId":"204834500","userId":"000002","jokeId":"2048345","content":"hhhh","postTime":"Sep 20, 2020 3:03:33 PM","user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600532085928.jpg","type":true,"status":true}}]}
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
         * jokeId : 2048345
         * userId : 000002
         * title : 测试
         * coverImg : img/joke/000002_1600582443704.jpg
         * postTime : Sep 20, 2020 2:14:07 PM
         * content : 测试
         * source : NPE
         * assortId : 1
         * collete : true
         * remarks : [{"remarkId":"204834500","userId":"000002","jokeId":"2048345","content":"hhhh","postTime":"Sep 20, 2020 3:03:33 PM","user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600532085928.jpg","type":true,"status":true}}]
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
        private List<RemarksBean> remarks;

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

        public List<RemarksBean> getRemarks() {
            return remarks;
        }

        public void setRemarks(List<RemarksBean> remarks) {
            this.remarks = remarks;
        }

        public static class RemarksBean {
            /**
             * remarkId : 204834500
             * userId : 000002
             * jokeId : 2048345
             * content : hhhh
             * postTime : Sep 20, 2020 3:03:33 PM
             * user : {"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600532085928.jpg","type":true,"status":true}
             */

            private String remarkId;
            private String userId;
            private String jokeId;
            private String content;
            private String postTime;
            private UserBean user;

            public String getRemarkId() {
                return remarkId;
            }

            public void setRemarkId(String remarkId) {
                this.remarkId = remarkId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getJokeId() {
                return jokeId;
            }

            public void setJokeId(String jokeId) {
                this.jokeId = jokeId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPostTime() {
                return postTime;
            }

            public void setPostTime(String postTime) {
                this.postTime = postTime;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                /**
                 * userId : 000002
                 * phone : 1
                 * name : NPE
                 * password : 1
                 * icon : img/user/000002_1600532085928.jpg
                 * type : true
                 * status : true
                 */

                private String userId;
                private String phone;
                private String name;
                private String password;
                private String icon;
                private boolean type;
                private boolean status;

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public boolean isType() {
                    return type;
                }

                public void setType(boolean type) {
                    this.type = type;
                }

                public boolean isStatus() {
                    return status;
                }

                public void setStatus(boolean status) {
                    this.status = status;
                }
            }
        }
    }
}
