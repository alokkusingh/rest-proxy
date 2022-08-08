package com.alok.proxy.restproxy.listener;

import com.alok.mqtt.listener.MqttCallbackListener;
import com.alok.mqtt.payload.RequestPayload;
import com.alok.mqtt.payload.ResponsePayload;
import com.alok.mqtt.processor.RequestProcessor;
import com.alok.mqtt.processor.ResponseProcessor;
import com.alok.mqtt.service.MqttClientService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Optional;

@Slf4j
public class CustomMqttCallbackListener extends MqttCallbackListener {

    private ResponseProcessor responseProcessor;

    public CustomMqttCallbackListener() {
        super();
    }
    public CustomMqttCallbackListener(ResponseProcessor responseProcessor) {
        this();
        this.responseProcessor = responseProcessor;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("Message arrived - {} - {}", topic, mqttMessage);
        ResponsePayload responsePayload = null;
        try {
            responsePayload = this.responseProcessor.parseMqttRequest(mqttMessage.getPayload());

        } catch (Exception e) {
            log.error("Error processing payload, error: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            responsePayload = Optional.ofNullable(responsePayload).orElse(new ResponsePayload());
            log.info("ResponsePayload - {}", responsePayload);
            responseProcessor.processResponse(responsePayload);
        }
    }
}
