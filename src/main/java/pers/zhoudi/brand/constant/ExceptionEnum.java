package pers.zhoudi.brand.constant;

/**
 * 异常信息所对应的枚举类（与properties文件对应）
 *
 * @author : 周迪
 * @date : 2024/06/26
 */
public enum ExceptionEnum {
    // 以下为所有异常信息的名称，其与properties文件里的异常信息名称对应
    DATA_EMPTY("data.empty"),
    DATA_PROPERTIES_UNCOMPLETED("data.properties.uncompleted"),
    DATA_UNMATCHED("data.unmatched"),
    DATA_NOT_EXISTED("data.not.existed");

    // 异常信息的名称
    private final String exceptionMessageKey;

    ExceptionEnum(String exceptionMessageKey) {
        this.exceptionMessageKey = exceptionMessageKey;
    }

    /**
     * // 获取异常信息键值
     * @return
     */
    public String getExceptionMessageKey() {
        return exceptionMessageKey;
    }
}
