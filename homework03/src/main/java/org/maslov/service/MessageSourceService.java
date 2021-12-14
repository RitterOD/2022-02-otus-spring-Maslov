package org.maslov.service;

import org.maslov.configuration.AppProperties;
import org.maslov.util.AppConstants;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceService {
    private final MessageSource messageSource;
    private final CurrentLocaleInMemoryHolder currentLocaleInMemoryHolder;



    public MessageSourceService(MessageSource messageSource, AppProperties properties, CurrentLocaleInMemoryHolder currentLocaleInMemoryHolder) {
        this.messageSource = messageSource;
        this.currentLocaleInMemoryHolder = currentLocaleInMemoryHolder;
    }

    public String getMessage(String s, Object... objects) {
        return messageSource.getMessage(s, objects, currentLocaleInMemoryHolder.getCurrentLocale());
    }

//    public String getMessage(String s) {
//        return getMessage(s, new Object[0]);
//    }

}
