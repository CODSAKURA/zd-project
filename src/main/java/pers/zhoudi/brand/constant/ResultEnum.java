package pers.zhoudi.brand.constant;

public enum ResultEnum {
    DATA_EMPTY("data.empty"),
    DATA_PROPERTIES_UNCOMPLETED("data.properties.uncompleted"),
    DATA_UNMATCHED("data.unmatched"),
    DATA_NOT_EXISTED("data.not.existed");

    private final String exceptionMessageKey;

    ResultEnum(String exceptionMessageKey) {
        this.exceptionMessageKey = exceptionMessageKey;
    }

    public String getExceptionMessageKey() {
        return exceptionMessageKey;
    }
}
