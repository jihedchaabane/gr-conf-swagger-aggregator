package com.chj.gr.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Configuration;

import com.chj.gr.service.SwaggerService;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaEvent;
import com.netflix.discovery.EurekaEventListener;

//import lombok.RequiredArgsConstructor;

@Configuration
//@RequiredArgsConstructor
public class EurekaEventListenerConfig {
	
    private static final Logger logger = LoggerFactory.getLogger(EurekaEventListenerConfig.class);
    private final EurekaClient eurekaClient;
    private final SwaggerService swaggerService;
    private final SwaggerUiConfigProperties swaggerUiConfig;
    
    private final SseController sseController;

    public EurekaEventListenerConfig(EurekaClient eurekaClient, SwaggerService swaggerService,
			SwaggerUiConfigProperties swaggerUiConfig,
			SseController sseController) {
		this.eurekaClient = eurekaClient;
		this.swaggerService = swaggerService;
		this.swaggerUiConfig = swaggerUiConfig;
		this.sseController = sseController;
	}

	@PostConstruct
    public void registerEurekaEventListener() {
        EurekaEventListener eventListener = new EurekaEventListener() {
            @Override
            public void onEvent(EurekaEvent event) {
                logger.info("Eureka event received: {}", event.getClass().getSimpleName());
                updateSwaggerUrls();
            }
        };
        eurekaClient.registerEventListener(eventListener);
        logger.info("Registered Eureka event listener for dynamic Swagger updates");
        updateSwaggerUrls(); // Initialisation des URLs au démarrage
    }

    private void updateSwaggerUrls() {
        logger.info("Updating Swagger URLs due to Eureka registry change");
        Set<SwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        swaggerService.getSwaggerResources().forEach(resource -> {
            urls.add(new SwaggerUiConfigProperties.SwaggerUrl(
                resource.getName(), resource.getUrl(), resource.getName()
            ));
            logger.info("Updated Swagger resource: name={}, url={}", resource.getName(), resource.getUrl());
        });
        swaggerUiConfig.setUrls(urls);
        logger.info("Configured {} Swagger URLs", urls.size());
        
        sseController.sendEurekaUpdateEvent(); // Notifier les clients via SSE.
    }
}
