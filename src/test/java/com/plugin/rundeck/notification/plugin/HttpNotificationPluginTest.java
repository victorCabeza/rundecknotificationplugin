package com.plugin.rundeck.notification.plugin;

import org.apache.http.client.HttpClient;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

public class HttpNotificationPluginTest {

    private static final String TRIGGER = "trigger";
    private static final String GET = "GET";
    private static final String URL_VALUE = "http://localhost";
    private static final String APPLICATION_JSON = "application/json";

    private HttpNotificationPlugin plugin;
    @Mock
    private HttpClient httpClient;

    @Before
    public void init() {
        this.httpClient = Mockito.mock(HttpClient.class);
        this.plugin = getPlugin();
    }

    @Test
    public void givenGetMethodWhenRequestThen200() throws IOException {
        // given
        final Map<String, Object> body = null;
        final Map<String, Object> config = getGetConfig();
        mockReturnCode(HttpStatus.SC_OK);

        // when
        final boolean result = this.plugin.postNotification(TRIGGER, body, config);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void givenGetMethodWhenRequestThen500() throws IOException {
        // given
        final Map<String, Object> body = null;
        final Map<String, Object> config = getGetConfig();

        mockReturnCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        // when
        final boolean result = this.plugin.postNotification(TRIGGER, body, config);

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void givenGetMethodWhenRequestThenException() throws IOException {
        // given
        final Map<String, Object> body = null;
        final Map<String, Object> config = getGetConfig();

        mockReturnException();

        // when
        final boolean result = this.plugin.postNotification(TRIGGER, body, config);

        // then
        Assert.assertFalse(result);
    }

    private void mockReturnCode(int httpResultCode) throws IOException {
        final HttpResponse mockHttpResponse = Mockito.mock(HttpResponse.class);
        final StatusLine statusLine = Mockito.mock(StatusLine.class);
        Mockito.when(this.httpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
        Mockito.when(statusLine.getStatusCode()).thenReturn(httpResultCode);
        Mockito.when(mockHttpResponse.getStatusLine()).thenReturn(statusLine);
    }

    private void mockReturnException() throws IOException {
        Mockito.when(this.httpClient.execute(Mockito.any())).thenThrow(new IOException());
    }

    private Map<String, Object> getGetConfig() {
        final Map<String, Object> config = new HashMap<>();
        config.put(RequestConfig.METHOD, GET);
        config.put(RequestConfig.URL, URL_VALUE);
        config.put(RequestConfig.CONTENT_TYPE, APPLICATION_JSON);
        return config;
    }

    private HttpNotificationPlugin getPlugin() {
        return new HttpNotificationPlugin.Builder().withHttpClient(this.httpClient).build();
    }
}
