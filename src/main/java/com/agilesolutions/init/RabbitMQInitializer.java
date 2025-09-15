package com.agilesolutions.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.server.SystemLauncher;
import org.apache.qpid.server.configuration.IllegalConfigurationException;
import org.apache.qpid.server.model.SystemConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RabbitMQInitializer implements InitializingBean {

    private static final String DEFAULT_INITIAL_CONFIGURATION_LOCATION = "qpid-config.json";

    private SystemLauncher systemLauncher = new SystemLauncher();

    private Map<String, Object> createSystemConfig() throws IllegalConfigurationException {
        Map<String, Object> attributes = new HashMap<>();
        URL initialConfigUrl = RabbitMQInitializer.class.getClassLoader().getResource(DEFAULT_INITIAL_CONFIGURATION_LOCATION);
        if (initialConfigUrl == null) {
        throw new IllegalConfigurationException("Configuration location '" + DEFAULT_INITIAL_CONFIGURATION_LOCATION + "' not found");
        }
        attributes.put(SystemConfig.TYPE, "Memory");
        attributes.put(SystemConfig.INITIAL_CONFIGURATION_LOCATION, initialConfigUrl.toExternalForm());
        attributes.put(SystemConfig.STARTUP_LOGGED_TO_SYSTEM_OUT, true);
        return attributes;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        systemLauncher.startup(createSystemConfig());

        log.info("Embedded RabbitMQ server started successfully-------");


    }
}