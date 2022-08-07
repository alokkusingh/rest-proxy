package com.alok.proxy.restproxy.cache;

import com.alok.mqtt.payload.ResponsePayload;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Data
@Component
public class RequestCache {
    private Map<String, CompletableFuture<ResponsePayload>> responseCache = new HashMap<>();
}
