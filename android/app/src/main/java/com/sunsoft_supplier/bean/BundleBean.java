package com.sunsoft_supplier.bean;

/**
 * Bundle更新bean
 * Created by MJX on 2017/4/12.
 */
public class BundleBean {
    private String msgCode;

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    private ObjBean obj;
    public  class ObjBean{
        private String title;
        private String message;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public BodyBean getBody() {
            return body;
        }

        public void setBody(BodyBean body) {
            this.body = body;
        }

        private BodyBean body;
    }

    public  class BodyBean{
        private String versionCode;
        private String bundleCode;
        private String type;
        private String bundleName;
        private String url;
        private String hashCode;
        private String content;
        private String sha1;
        private String isIncrement;

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getBundleCode() {
            return bundleCode;
        }

        public void setBundleCode(String bundleCode) {
            this.bundleCode = bundleCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBundleName() {
            return bundleName;
        }

        public void setBundleName(String bundleName) {
            this.bundleName = bundleName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHashCode() {
            return hashCode;
        }

        public void setHashCode(String hashCode) {
            this.hashCode = hashCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSha1() {
            return sha1;
        }

        public void setSha1(String sha1) {
            this.sha1 = sha1;
        }

        public String getIsIncrement() {
            return isIncrement;
        }

        public void setIsIncrement(String isIncrement) {
            this.isIncrement = isIncrement;
        }

    }
}
