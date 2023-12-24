package com.hxh.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequestHelper {

    // 发送GET请求
    public static String sendGetRequest(String url) {
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.setRequestMethod("GET");

            // 读取响应
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 发送POST请求，参数以JSON格式
    public static String sendPostRequest(String url, String jsonInputString) {
        try {
        URL postUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");

        // 将JSON参数写入请求
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 测试
    public static void main(String[] args) {
        HttpRequestHelper helper = new HttpRequestHelper();

//        try {
//            // 发送GET请求
//            String getResponse = helper.sendGetRequest("https://example.com/api/data?param1=value1&param2=value2");
//            System.out.println("GET Response: " + getResponse);
//
//            // 发送POST请求
//            String postResponse = helper.sendPostRequest("https://example.com/api/post", "{\"key\":\"value\"}");
//            System.out.println("POST Response: " + postResponse);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
