package com.sunsoft_supplier.bean;

import java.io.Serializable;

/**
 * 请求服务器地址和更新Apk的bean
 * Created by MJX on 2017/4/12.
 */
public class UpdateBean implements Serializable {
    private String msgCode;
    private ObjBean obj;

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

    public class ObjBean {
        private String title;

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

        private String message;
        private BodyBean body;
    }

    public class BodyBean {
        private String versionCode;
        private String type;
        private String url;
        private String serverUrl;
        private String content;
        private String bundleCode;
        private String bundleName;
        private String hashCode;
        private String sha1;
        private String isIncrement;
        private String versionId;//版本号唯一标识
        private String bundleId;

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getServerUrl() {
            return serverUrl;
        }

        public void setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBundleCode() {
            return bundleCode;
        }

        public void setBundleCode(String bundleCode) {
            this.bundleCode = bundleCode;
        }

        public String getBundleName() {
            return bundleName;
        }

        public void setBundleName(String bundleName) {
            this.bundleName = bundleName;
        }

        public String getHashCode() {
            return hashCode;
        }

        public void setHashCode(String hashCode) {
            this.hashCode = hashCode;
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

        public String getVersionId() {
            return versionId;
        }

        public void setVersionId(String versionId) {
            this.versionId = versionId;
        }

        public String getBundleId() {
            return bundleId;
        }

        public void setBundleId(String bundleId) {
            this.bundleId = bundleId;
        }
    }
}
