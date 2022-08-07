package com.alok.proxy.restproxy.controller;

import com.alok.mqtt.payload.ResponsePayload;
import com.alok.proxy.restproxy.cache.RequestCache;
import com.alok.proxy.restproxy.service.FinService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/")
public class FinController {

    @Autowired
    private FinService finService;

    @Autowired
    private RequestCache requestCache;

    @GetMapping(value = {"{a}/{b}", "{a}/{b}/{c}", "{a}/{b}/{c}/{d}", "{a}/{b}/{c}/{d}/{e}"})
    public ResponseEntity<String> getEndpoint(
            @RequestHeader Map<String, String> headers,
            @RequestParam Map<String, String>  queries,
            @PathVariable(required = true) String a,
            @PathVariable(required = true) String b,
            @PathVariable(required = false) String c,
            @PathVariable(required = false) String d,
            @PathVariable(required = false) String e
    ) throws MqttException, ExecutionException, InterruptedException {

        log.info("Request received!!");
        CompletableFuture<ResponsePayload> future = finService.processGetRequest(headers, queries, a, b, c, d, e);

        log.info("Waiting for response!");
        ResponsePayload responsePayload = future.get();
        log.info("Response received!");

        ResponseEntity<String> response = ResponseEntity.ok(new String(Base64.getDecoder().decode(responsePayload.getBody())));

        return response;
    }
}
