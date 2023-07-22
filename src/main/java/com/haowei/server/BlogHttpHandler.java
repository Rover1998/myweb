package com.haowei.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BlogHttpHandler implements HttpHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(BlogHttpHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LOGGER.info("MyApplication receive a {} request from IP: {} for path: {}", exchange.getRequestMethod(), exchange.getRemoteAddress(), exchange.getRequestURI());
        InputStream is = getClass().getClassLoader().getResourceAsStream("HelloHtml.html");
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sb = new StringBuffer();
        br.lines().forEach(s -> sb.append(s.trim()));
        br.close();
        String response = sb.toString();
        LOGGER.info("Try to send response: {}", response);
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Content-Type","text/html");
        responseHeaders.add("charset","UTF-8");
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.close();
    }
}
