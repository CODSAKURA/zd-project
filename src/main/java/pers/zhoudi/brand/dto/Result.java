package pers.zhoudi.brand.dto;

/**
 * 将数据进行特殊封装（目的：传递给前端）
 * @author : 周迪
 * @date : 2024/05/09
 */
public class Result {
    // 数据
    private Object data;

    // 状态码
    private int code;

    public Result(int code) {
        this.code = code;
    }

    public Result(Object data, int code) {
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
