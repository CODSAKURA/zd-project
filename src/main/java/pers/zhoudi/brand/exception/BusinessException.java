package pers.zhoudi.brand.exception;

import pers.zhoudi.brand.constant.ExceptionEnum;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 异常处理类
 *
 * @author : 周迪
 * @date : 2024/06/26
 */
public class BusinessException extends RuntimeException {
    /**
     * 构造函数，接受一个异常枚举类型，并调用父类构造函数
     * @param message 异常信息名称
     */
    public BusinessException(ExceptionEnum message) {
        super(getLocalizedMessage(message.getExceptionMessageKey()));
    }

    /**
     * 获取本地化的异常消息
     * @param messageKey 异常消息的键值
     * @return 本地化的异常消息
     */
    private static String getLocalizedMessage(String messageKey) {
        // 获取默认语言环境的资源包
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());
        // 返回资源包中对应键的值
        return bundle.getString(messageKey);
    }
}
