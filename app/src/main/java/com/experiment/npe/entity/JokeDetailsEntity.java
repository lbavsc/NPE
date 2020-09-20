package com.experiment.npe.entity;

import java.util.List;

public class JokeDetailsEntity {


    /**
     * code : 1001
     * msg : 获取成功
     * data : {"jokeId":"2095385","userId":"000002","title":"test","coverImg":"img/joke/000002_1600618149501.jpg","postTime":"Sep 21, 2020 12:09:16 AM","content":"test","source":"NPE","assortId":1,"collete":true,"remarks":[{"remarkId":"209538501","userId":"000002","jokeId":"2095385","content":"提交","postTime":"Sep 21, 2020 12:17:53 AM","user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}},{"remarkId":"209538500","userId":"000002","jokeId":"2095385","content":"测试","postTime":"Sep 21, 2020 12:17:49 AM","user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}}],"user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}}
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
         * jokeId : 2095385
         * userId : 000002
         * title : test
         * coverImg : img/joke/000002_1600618149501.jpg
         * postTime : Sep 21, 2020 12:09:16 AM
         * content : test
         * source : NPE
         * assortId : 1
         * collete : true
         * remarks : [{"remarkId":"209538501","userId":"000002","jokeId":"2095385","content":"提交","postTime":"Sep 21, 2020 12:17:53 AM","user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}},{"remarkId":"209538500","userId":"000002","jokeId":"2095385","content":"测试","postTime":"Sep 21, 2020 12:17:49 AM","user":{"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}}]
         * user : {"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}
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
        private UserBean user;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<RemarksBean> getRemarks() {
            return remarks;
        }

        public void setRemarks(List<RemarksBean> remarks) {
            this.remarks = remarks;
        }

        public static class UserBean {
            /**
             * userId : 000002
             * phone : 1
             * name : NPE
             * password : 1
             * icon : img/user/000002_1600617942247.jpg
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

        public static class RemarksBean {
            /**
             * remarkId : 209538501
             * userId : 000002
             * jokeId : 2095385
             * content : 提交
             * postTime : Sep 21, 2020 12:17:53 AM
             * user : {"userId":"000002","phone":"1","name":"NPE","password":"1","icon":"img/user/000002_1600617942247.jpg","type":true,"status":true}
             */

            private String remarkId;
            private String userId;
            private String jokeId;
            private String content;
            private String postTime;
            private UserBeanX user;

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

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public static class UserBeanX {
                /**
                 * userId : 000002
                 * phone : 1
                 * name : NPE
                 * password : 1
                 * icon : img/user/000002_1600617942247.jpg
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
