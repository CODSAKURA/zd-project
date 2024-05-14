package com.project.frontend;

/**
 * 封装数据传递给前端
 * @author : 周迪
 * @date : 2024/05/09
 */
public class FrontendResponseResult {
    // 数据
    private Object data;

    // 状态码
    private int code;

    public FrontendResponseResult(int code) {
        this.code = code;
    }

    public FrontendResponseResult(Object data, int code) {
        this.data = data;
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
