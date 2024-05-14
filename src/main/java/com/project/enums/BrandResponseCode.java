package com.project.enums;

/**
 * 响应给前端的状态码枚举类-品牌
 * @author : 周迪
 * @date : 2024/05/10
 */
public enum BrandResponseCode {
    // 成功按1结尾
    SELECT_OK(30031),
    ADD_OK(30041),
    UPDATE_OK(30051),
    DELETE_OK(30061),

    // 失败按0结尾
    SELECT_ERROR(30030),
    ADD_ERROR(30040),
    UPDATE_ERROR(30050),
    DELETE_ERROR(30060);

    private int code;

    BrandResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
