package com.alok.proxy.restproxy.service;

import com.alok.mqtt.payload.RequestPayload;
import com.alok.mqtt.payload.ResponsePayload;
import com.alok.mqtt.service.MqttClientService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class FinService {

    @Autowired
    private MqttClientService mqttClientService;
    @Async
    public void processGetRequest(
            Map<String, String> headers,
            Map<String, String> queries,
            String pathA, String pathB, String pathC, String pathD, String pathE
    ) throws MqttException {

         mqttClientService.publish(new RequestPayload(
                    HttpMethod.GET,
                    prepareHttpUrl(queries, pathA, pathB, pathC, pathD, pathE),
                    null,
                    UUID.randomUUID().toString()
         ));

    }

    private String prepareHttpUrl(
            Map<String, String> queries,
            String pathA, String pathB, String pathC, String pathD, String pathE
    ) {
        String endpoint = List.of(pathA, pathB,
                        Optional.ofNullable(pathC).orElse("NULL"),
                        Optional.ofNullable(pathD).orElse("NULL"),
                        Optional.ofNullable(pathE).orElse("NULL")).stream()
                .filter(path -> path != "NULL")
                .reduce("", (a, b) -> a + "/" + b);

        return endpoint;
    }
}
