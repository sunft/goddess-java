package com.bjike.goddess.user.to;

import com.bjike.goddess.common.api.to.BaseTO;

import java.io.Serializable;

/**
 * 用户业务传送对象
 *
 * @Author: [liguiqin]
 * @Date: [2017-01-18 15:25]
 * @Description: []
 * @Version: [1.0.0]
 * @Copy: [com.bjike]
 */
public class UserTO extends BaseTO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录手机(注册验证手机)
     */
    private String phone;
    /**
     * 登录邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String headSculpture;
    /**
     * 昵称
     */
    private String nickname;

    private String createTime;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadSculpture() {
        return headSculpture;
    }

    public void setHeadSculpture(String headSculpture) {
        this.headSculpture = headSculpture;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
