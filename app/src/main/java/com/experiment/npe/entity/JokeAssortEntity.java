package com.experiment.npe.entity;

import java.util.List;

public class JokeAssortEntity {


    /**
     * code : 1001
     * msg : 获取成功
     * data : [{"assortId":"1","assortName":"全部"},{"assortId":"2","assortName":"娱乐"},{"assortId":"3","assortName":"科技"},{"assortId":"4","assortName":"财经"},{"assortId":"5","assortName":"国际"},{"assortId":"6","assortName":"时尚"},{"assortId":"7","assortName":"健康"},{"assortId":"8","assortName":"软件"},{"assortId":"9","assortName":"旅游"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * assortId : 1
         * assortName : 全部
         */

        private String assortId;
        private String assortName;

        public String getAssortId() {
            return assortId;
        }

        public void setAssortId(String assortId) {
            this.assortId = assortId;
        }

        public String getAssortName() {
            return assortName;
        }

        public void setAssortName(String assortName) {
            this.assortName = assortName;
        }
    }
}
