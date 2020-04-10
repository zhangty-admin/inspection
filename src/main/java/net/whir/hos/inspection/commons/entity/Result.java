package net.whir.hos.inspection.commons.entity;

import java.io.Serializable;

/**
 * @Author: zty
 * @Date: 2020/4/7 10:14 上午
 * 返回结果实体类
 */
public class Result implements Serializable{

    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回消息

    private Object data;//返回数据

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功";
    }

    public Result(Object data) {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功";
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
