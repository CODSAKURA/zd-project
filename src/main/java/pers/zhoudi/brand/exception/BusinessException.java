package pers.zhoudi.brand.exception;

import pers.zhoudi.brand.constant.ResultEnum;

import java.util.Locale;
import java.util.ResourceBundle;

public class BusinessException extends RuntimeException {
    public BusinessException(ResultEnum message) {
        super(getLocalizedMessage(message.getExceptionMessageKey()));
    }

    private static String getLocalizedMessage(String messageKey) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());
        return bundle.getString(messageKey);
    }
}
