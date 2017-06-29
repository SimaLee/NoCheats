package com.simalee.nocheats.common.base;

/**
 * Created by Lee Sima on 2017/6/29.
 */

public class Response {

    private int type;
    private String error;

    public static final int TYPE_WRONG_PCODE = 1;
    public static final int TYPE_ACCOUNT_EXIST = 2;
    public static final int TYPE_SYSTEM_ERROR = 3;

    public Response() {
    }

    public Response(int type, String error) {
        this.error = error;
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
