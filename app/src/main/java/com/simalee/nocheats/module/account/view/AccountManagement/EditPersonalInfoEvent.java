package com.simalee.nocheats.module.account.view.AccountManagement;

/**
 * Created by Yang on 2017/7/2.
 */

public class EditPersonalInfoEvent {
    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
    //type 1头像，2name,3sex,4sign
    public int type ;
    public String msg;
    public EditPersonalInfoEvent(int t,String message){
        type = t;
        msg = message;
    }

}
