package com.alok.proxy.restproxy.service;

import com.alok.mqtt.payload.RequestPayload;
import com.alok.mqtt.payload.ResponsePayload;
import com.alok.mqtt.service.MqttClientService;
import com.alok.proxy.restproxy.cache.RequestCache;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FinService {

    @Autowired
    private MqttClientService mqttClientService;

    @Autowired
    private RequestCache requestCache;

    public ResponsePayload processGetRequest(
            Map<String, String> headers,
            Map<String, String> queries,
            String pathA, String pathB, String pathC, String pathD, String pathE
    ) throws MqttException, ExecutionException, InterruptedException {

         String correlationId = UUID.randomUUID().toString();
         CompletableFuture<ResponsePayload> completableFuture = new CompletableFuture<>();
         requestCache.getResponseCache().put(correlationId, completableFuture);
         mqttClientService.publish(new RequestPayload(
                 HttpMethod.GET,
                 prepareHttpUrl(queries, pathA, pathB, pathC, pathD, pathE),
                 null,
                 correlationId
         ));

         ResponsePayload responsePayload = completableFuture.get();
         return responsePayload;
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

        return endpoint + prepareQueryString(queries);
    }

    private String prepareQueryString(Map<String, String> queries) {
        if (queries == null || queries.isEmpty())
            return "";
        return queries.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce("?", (a,b) -> a + "&" + b);
    }
}
