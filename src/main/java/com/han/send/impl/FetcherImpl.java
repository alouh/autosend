package com.han.send.impl;

import com.han.send.Fetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/4
 */
@Component
public class FetcherImpl implements Fetcher {
    @Value("${loginName}")
    private String loginName;
    @Value("${password}")
    private String password;

    private HttpClient httpClient;
    private HttpRequest.Builder requestBuilder;

    public FetcherImpl() {
        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        requestBuilder = HttpRequest.newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .header("Accept", "application/json, text/plain, */*")
                .header("Content-Type", "application/json;charset=UTF-8");
    }

    @Override
    public void login() throws FetcherException {
        String requestBody = "{\n" +
                "  \"username\": \"" + loginName + "\",\n" +
                "  \"password\": \"" + password + "\"\n" +
                "}";
        HttpRequest httpRequest = requestBuilder
                .uri(URI.create("http://10.76.84.18/api/Account/Login"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = httpResponse.body();
            info(body);
        } catch (IOException | InterruptedException e) {
            throw new FetcherException("登录失败", e);
        }
    }

    private void info(String s) {
        System.out.println(s);
    }
}
