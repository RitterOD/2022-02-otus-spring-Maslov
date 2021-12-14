package org.maslov.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "application.question")
@Component
public class AppProperties {


    private String startLocaleTag;
    private String source;
    private String bundlePath;



    private List<String> supportedLocaleTags;

    public String getBundlePath() {
        return bundlePath;
    }

    public void setBundlePath(String bundlePath) {
        this.bundlePath = bundlePath;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStartLocaleTag() {
        return startLocaleTag;
    }

    public void setStartLocaleTag(String startLocaleTag) {
        this.startLocaleTag = startLocaleTag;
    }

    public List<String> getSupportedLocaleTags() {
        return supportedLocaleTags;
    }

    public void setSupportedLocaleTags(List<String> supportedLocaleTags) {
        this.supportedLocaleTags = supportedLocaleTags;
    }
}
