package com.elwyn.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/13.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -7030312179852210165L;

    private String cnUserId;
    private String cnUserName;
    private String cnUserPassword;
    private String cnUserToken;
    private String cnUserNick;

    public String getCnUserId() {
        return cnUserId;
    }

    public void setCnUserId(String cnUserId) {
        this.cnUserId = cnUserId;
    }

    public String getCnUserName() {
        return cnUserName;
    }

    public void setCnUserName(String cnUserName) {
        this.cnUserName = cnUserName;
    }

    public String getCnUserPassword() {
        return cnUserPassword;
    }

    public void setCnUserPassword(String cnUserPassword) {
        this.cnUserPassword = cnUserPassword;
    }

    public String getCnUserToken() {
        return cnUserToken;
    }

    public void setCnUserToken(String cnUserToken) {
        this.cnUserToken = cnUserToken;
    }

    public String getCnUserNick() {
        return cnUserNick;
    }

    public void setCnUserNick(String cnUserNick) {
        this.cnUserNick = cnUserNick;
    }
}
