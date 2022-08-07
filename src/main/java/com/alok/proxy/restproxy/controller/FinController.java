package com.alok.proxy.restproxy.controller;

import com.alok.proxy.restproxy.service.FinService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
public class FinController {

    @Autowired
    private FinService finService;

    @GetMapping(value = {"{a}/{b}", "{a}/{b}/{c}", "{a}/{b}/{c}/{d}", "{a}/{b}/{c}/{d}/{e}"})
    public ResponseEntity<Void> getEndpoint(
            @RequestHeader Map<String, String> headers,
            @RequestParam Map<String, String>  queries,
            @PathVariable(required = true) String a,
            @PathVariable(required = true) String b,
            @PathVariable(required = false) String c,
            @PathVariable(required = false) String d,
            @PathVariable(required = false) String e
    ) throws MqttException {

        finService.processGetRequest(headers, queries, a, b, c, d, e);

        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
