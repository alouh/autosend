package com.han.send.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.han.entity.Customer;
import com.han.send.Fetcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    @Value("${successCode}")
    private int successCode;
    private String token;

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
            JSONObject jsonObject = JSON.parseObject(body);
            token = jsonObject.getString("token");
            info(body);
        } catch (IOException | InterruptedException e) {
            throw new FetcherException("登录失败", e);
        }
    }

    @Override
    public List<Customer> selectCustomers(int pageIndex, int pageSize, long startDate, long endDate, String category) throws FetcherException {
        String requestBody = String.format("{\n  \"pageindex\": %d,\n  \"pagesize\": %d,\n  \"startdate\": \"%d\",\n  \"enddate\": \"%d\",\n  \"orgacc\": \"\",\n  \"category\": \"%s\"\n}", pageIndex, pageSize, startDate, endDate, category);
        HttpRequest httpRequest = requestBuilder
                .uri(URI.create("http://10.76.84.18/api/Company/CompaniesData"))
                .setHeader("token", token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = httpResponse.body();
            JSONObject jsonObject = JSON.parseObject(body);
            int code = jsonObject.getInteger("code");
            if (code != successCode) {
                throw new FetcherException(MessageFormat.format("获取数据失败,{0}", ""));
            }
            return parseCustomers(jsonObject);
        } catch (IOException | InterruptedException e) {
            throw new FetcherException("登录失败", e);
        }
    }

    private List<Customer> parseCustomers(JSONObject jsonObject) {
        List<Customer> customers = new ArrayList<>();
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray dataArray = dataObject.getJSONArray("data");
        for (Object object : dataArray) {
            JSONObject customerJson = (JSONObject) object;
            int status = getOrDefault(customerJson, "status", 0);
            String code = getOrDefault(customerJson, "organization", "");
            String name = getOrDefault(customerJson, "orgName", "");
            int total = getOrDefault(customerJson, "total", 0);
            int success = getOrDefault(customerJson, "success", 0);
            int failure = getOrDefault(customerJson, "failed", 0);
            String successRate = getOrDefault(customerJson, "rate", "");
            Customer customer = new Customer(status, code, name, total, success, failure, successRate);
            customers.add(customer);
        }
        Collections.sort(customers);
        return customers;
    }

    /**
     * 从jsonObject获取指定key的值,如果没有,使用defaultValue
     *
     * @param jsonObject   jsonObject
     * @param key          key
     * @param defaultValue defaultValue
     * @param <T>          类型
     * @return value
     */
    @SuppressWarnings("unchecked")
    private <T> T getOrDefault(JSONObject jsonObject, String key, T defaultValue) {
        T t;
        if (defaultValue instanceof Integer) {
            t = (T) jsonObject.getInteger(key);
        } else if (defaultValue instanceof Long) {
            t = (T) jsonObject.getLong(key);
        } else if (defaultValue instanceof Double) {
            t = (T) jsonObject.getDouble(key);
        } else if (defaultValue instanceof String) {
            t = (T) jsonObject.getString(key);
        } else {
            throw new IllegalArgumentException("不支持的类型");
        }
        if (Objects.isNull(t)) {
            t = defaultValue;
        }
        return t;
    }

    private void info(String s) {
        System.out.println(s);
    }
}
