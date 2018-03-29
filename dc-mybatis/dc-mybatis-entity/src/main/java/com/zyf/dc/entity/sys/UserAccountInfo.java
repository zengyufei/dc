package com.zyf.dc.entity.sys;

/**
 * @version V1.0.0
 */
public class UserAccountInfo implements LoginDetail, TokenDetail {

    private String username;
    private String password;
    private String authorities;
    private Long lastPasswordChange;
    private char enable;

    public UserAccountInfo() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Long getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Long lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public void setEnable(char enable) {
        this.enable = enable;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean enable() {
        if (this.enable == '1') {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserAccountInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities='" + authorities + '\'' +
                ", lastPasswordChange=" + lastPasswordChange +
                ", enable=" + enable +
                '}';
    }
}
