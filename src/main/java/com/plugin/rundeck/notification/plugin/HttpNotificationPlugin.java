package com.plugin.rundeck.notification.plugin;

import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.Map;

@com.dtolabs.rundeck.core.plugins.Plugin(service = "Notification", name = "rundecknotificationplugin")
@PluginDescription(title = "RundeckNotificationPlugin", description = "Rundeck http notification plugin")
public class HttpNotificationPlugin implements NotificationPlugin {



    /*@PluginProperty(name = "example",title = "Example String",description = "Example description")
    private String example;*/

    private HttpClient httpClient;

    private HttpNotificationPlugin(final Builder builder) {
        this.httpClient = builder.httpClient;
    }

    public boolean postNotification(final String trigger, final Map executionData, final Map config) {

        final RequestConfig requestConfig = new RequestConfig(config);

        final HttpUriRequest request = getRequest(requestConfig);

        final HttpResponse response;
        try {
            response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return false;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private HttpGet getRequest(final RequestConfig config) {
        final HttpGet request = new HttpGet(config.getUrl());
        request.addHeader(HttpHeaders.CONTENT_TYPE, config.getContentType());
        return request;
    }

    public static class Builder {
        private HttpClient httpClient;

        public Builder withHttpClient(final HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public HttpNotificationPlugin build() {
            return new HttpNotificationPlugin(this);
        }
    }
}