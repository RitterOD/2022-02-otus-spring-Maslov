package org.maslov.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.question")
@Component
public class AppProperties {
    private String startLocaleCode;
    private String source;
    private String bundlePath;

    public String getBundlePath() {
        return bundlePath;
    }

    public void setBundlePath(String bundlePath) {
        this.bundlePath = bundlePath;
    }

    public String getStartLocaleCode() {
        return startLocaleCode;
    }

    public void setStartLocaleCode(String startLocaleCode) {
        this.startLocaleCode = startLocaleCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
