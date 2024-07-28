package pers.zhoudi.project.constant;

/**
 * 响应给前端的状态码枚举类（User类所用）
 *
 * @author : 周迪
 * @date : 2024/05/10
 */
public enum UserConstant {

    // 以1结尾表明成功
    // 登录、注册、查询成功【状态码】
    LOGIN_OK(20021),
    REGISTER_OK(20031),
    USERNAME_NOT_EXIST(20041),

    // 以0结尾表明失败
    // 登录、注册、查询失败【状态码】
    LOGIN_ERROR(20020),
    REGISTER_ERROR(20030),
    USERNAME_EXIST(20040);

    private int code;

    UserConstant(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
