package com.haowei.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class APIHandler implements HttpHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(APIHandler.class);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("content-type", "text/plaintext");
        responseHeaders.add("charset", "UTF-8");
        responseHeaders.add("Access-Control-Allow-Origin", "http://localhost:3000");
        responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        String words = "Welcome, API invocation successes";
        LOGGER.info("Try to send response: {}", words);
        byte[] bytes = words.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream response = exchange.getResponseBody()) {
            response.write(bytes);
        }
    }
}
