package com.aqiu.onekey.bean;

/**
 * 更新实体类
 * Created by aqiu on 2017/3/10.
 */

public class Update {

    /**
     * appname : 爱新闻1.1
     * serverVersion : 2
     * serverFlag : 1
     * lastForce : 1
     * updateurl : http://192.168.1.103:8080/36Kr.apk
     * upgradeinfo : V1.1版本更新，你想不想要试一下哈！！！
     */

    private String appname;
    private int serverVersion;
    private String serverFlag;
    private String lastForce;
    private String updateurl;
    private String upgradeinfo;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public int getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(int serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getServerFlag() {
        return serverFlag;
    }

    public void setServerFlag(String serverFlag) {
        this.serverFlag = serverFlag;
    }

    public String getLastForce() {
        return lastForce;
    }

    public void setLastForce(String lastForce) {
        this.lastForce = lastForce;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getUpgradeinfo() {
        return upgradeinfo;
    }

    public void setUpgradeinfo(String upgradeinfo) {
        this.upgradeinfo = upgradeinfo;
    }
}
