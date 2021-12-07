package org.maslov.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class MessageSourceConfigure {

    @Bean
    public MessageSource messageSource(AppProperties properties) {
        ReloadableResourceBundleMessageSource messageSource  = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(properties.getBundlePath());
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean("systemIN")
    public InputStream systemIN() {
        return System.in;
    }

    @Bean("systemOut")
    public PrintStream systemOut() {
        return System.out;
    }
}
