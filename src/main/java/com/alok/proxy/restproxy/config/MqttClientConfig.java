package com.alok.proxy.restproxy.config;

import com.alok.mqtt.listener.MqttCallbackListener;
import com.alok.mqtt.processor.ResponseProcessor;
import com.alok.mqtt.service.MqttClientService;
import com.alok.proxy.restproxy.cache.RequestCache;
import com.alok.proxy.restproxy.listener.CustomMqttCallbackListener;
import com.alok.proxy.restproxy.processor.CustomResponseProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MqttClientConfig {

    private IotProperties iotProperties;
    private RequestCache requestCache;

    public MqttClientConfig(IotProperties iotProperties, RequestCache requestCache) {
        this.iotProperties = iotProperties;
        this.requestCache = requestCache;
    }


    @Bean(initMethod = "connect", destroyMethod = "disConnect")
    public MqttClientService mqttClientService() {

        MqttClientService mqttClientService = new MqttClientService();

        mqttClientService.setIotProperties(iotProperties);
        mqttClientService.setMqttCallbackListener(mqttCallbackListener());

        return mqttClientService;
    }


    public MqttCallbackListener mqttCallbackListener() {
        return new CustomMqttCallbackListener(responseProcessor());
    }

    @Bean
    public ResponseProcessor responseProcessor() {
        return new CustomResponseProcessor(
                objectMapper(),
                requestCache
        );
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


