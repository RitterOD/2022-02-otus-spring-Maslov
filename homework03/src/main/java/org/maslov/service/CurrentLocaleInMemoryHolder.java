package org.maslov.service;

import org.maslov.configuration.AppProperties;
import org.maslov.util.AppConstants;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CurrentLocaleInMemoryHolder {

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public String getCodeCurrenLocale() {
        return codeCurrenLocale;
    }

    private java.util.Locale currentLocale;
    private String codeCurrenLocale;

    public CurrentLocaleInMemoryHolder(AppProperties properties) {
        String code = properties.getStartLocaleTag();
        if (properties.getSupportedLocaleTags().contains(code)) {
            codeCurrenLocale = code;
            currentLocale = Locale.forLanguageTag(code);
        } else {
            throw new IllegalArgumentException("Wrong value ( " + code + " )of startLocale property");
        }
    }
}
