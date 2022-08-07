package com.alok.proxy.restproxy.processor;

import com.alok.mqtt.payload.ResponsePayload;
import com.alok.mqtt.processor.ResponseProcessor;
import com.alok.mqtt.service.MqttClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CustomResponseProcessor extends ResponseProcessor {

    private MqttClientService mqttClientService;

    public CustomResponseProcessor(
            ObjectMapper objectMapper
    ) {
        super(objectMapper);
    }

    @Override
    public String processResponse(ResponsePayload responsePayload) {
        log.info("Response received: {}", responsePayload);

        return null;
    }

}