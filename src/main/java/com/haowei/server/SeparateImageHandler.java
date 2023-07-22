package com.haowei.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.URI;

public class SeparateImageHandler implements HttpHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeparateImageHandler.class);

    @Override
    public void handle(HttpExchange exchange) {
        try {
            LOGGER.info("MyApplication receive a {} request from IP: {} for path: {}", exchange.getRequestMethod(), exchange.getRemoteAddress(), exchange.getRequestURI());
            URI requestURI = exchange.getRequestURI();
            if (requestURI.toString().startsWith("/image/")) {
                String filePath = requestURI.toString().substring(1);
                LOGGER.info("prepare to return image from path: {}", filePath);
                byte[] bytes = getClass().getClassLoader().getResourceAsStream(filePath).readAllBytes();
                LOGGER.info("check image byte array length {}", bytes.length);
                Headers responseHeaders = exchange.getResponseHeaders();
                responseHeaders.add("Content-Type", "image/jpeg");
                exchange.sendResponseHeaders(200, bytes.length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(bytes);
                outputStream.close();
            }
        } catch (Exception e) {
            LOGGER.error("Encounter error when resolve request: ", e);
        }

    }
}
