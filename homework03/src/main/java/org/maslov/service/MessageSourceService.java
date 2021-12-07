package org.maslov.service;

import org.maslov.configuration.AppProperties;
import org.maslov.util.AppConstants;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageSourceService {
    private final MessageSource messageSource;

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public String getCodeCurrenLocale() {
        return codeCurrenLocale;
    }

    private java.util.Locale currentLocale;
    private String codeCurrenLocale;


    public MessageSourceService(MessageSource messageSource, AppProperties properties) {
        this.messageSource = messageSource;
        String code = properties.getStartLocaleCode();
        codeCurrenLocale = code;
        switch (code) {
            case AppConstants.CODE_LOCALE_EN:
                currentLocale = AppConstants.LOCALE_EN;
                break;
            case AppConstants.CODE_LOCALE_RU:
                currentLocale = AppConstants.LOCALE_RU;
                break;
            default:
                throw new IllegalArgumentException("Wrong value ( " + code + " )of startLocale property");
        }
    }

    public String getMessage(String s, Object[] objects) {
        return messageSource.getMessage(s, objects, currentLocale);
    }

    public String getMessage(String s) {
        return getMessage(s, new Object[0]);
    }


}
