package com.simalee.nocheats.module.data.entity.user;

import com.simalee.nocheats.common.util.IntegralUtils;

/**
 * Created by Lee Sima on 2017/6/16.
 */

public class UserInfo {
    /**
     * 用户账号
     */
    String accunt;
    /**
     * 用户密码
     */
    String password;
    /**
     * 用户性别
     */
    String sex;
    /**
     * 用户个性签名
     */
    String sign;
    /**
     * 用户个人简介
     */
    String profile;

    /**
     * 用户生日
     */
    String birthday;

    /**
     * 用户平台点数 相当于积分
     */
    String point;

    public UserInfo(){

    }

    public String getAccunt() {
        return accunt;
    }

    public void setAccunt(String accunt) {
        this.accunt = accunt;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getLevel(){
        //TODO 确定积分体系
        return IntegralUtils.getLevel(point);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "accunt='" + accunt + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", sign='" + sign + '\'' +
                ", profile='" + profile + '\'' +
                ", birthday='" + birthday + '\'' +
                ", point='" + point + '\'' +
                '}';
    }
}
