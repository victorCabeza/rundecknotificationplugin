package com.plugin.rundeck.notification.plugin;

import java.util.Map;

public class RequestConfig {
    public static final String METHOD = "METHOD";
    public static final String URL = "URL";
    public static final String CONTENT_TYPE = "CONTENT-TYPE";

    private final String url;
    private final String contentType;
    private final HttpMethod method;

    public RequestConfig(final Map config) {
        this.url = getStringValue(config, URL);
        this.contentType = getStringValue(config, CONTENT_TYPE);
        this.method = HttpMethod.valueOf(getStringValue(config, METHOD));
    }

    private String getStringValue(final Map config, final String key) {
        return (String) config.get(key);
    }

    public String getUrl() {
        return url;
    }

    public String getContentType() {
        return contentType;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
