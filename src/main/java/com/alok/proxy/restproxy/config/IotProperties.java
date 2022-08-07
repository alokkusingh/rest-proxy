package com.alok.proxy.restproxy.config;

import com.alok.mqtt.config.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Profile;

@Data
@ConfigurationProperties(prefix = "iot")
@ConfigurationPropertiesScan
public class IotProperties extends Properties {

}
