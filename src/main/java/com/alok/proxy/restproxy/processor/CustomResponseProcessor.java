package com.alok.proxy.restproxy.processor;

import com.alok.mqtt.payload.ResponsePayload;
import com.alok.mqtt.processor.ResponseProcessor;
import com.alok.mqtt.service.MqttClientService;
import com.alok.proxy.restproxy.cache.RequestCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CustomResponseProcessor extends ResponseProcessor {

    private MqttClientService mqttClientService;

    private RequestCache requestCache;

    public CustomResponseProcessor(
            ObjectMapper objectMapper,
            RequestCache requestCache
    ) {
        super(objectMapper);
        this.requestCache = requestCache;
    }

    @Override
    public String processResponse(ResponsePayload responsePayload) {
        requestCache.getResponseCache().remove(responsePayload.getCorrelationId()).complete(responsePayload);
        return null;
    }

}