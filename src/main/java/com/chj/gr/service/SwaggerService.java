package com.chj.gr.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.EurekaClient;

@Service
public class SwaggerService {
	
    private static final Logger logger = LoggerFactory.getLogger(SwaggerService.class);
    
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    @Value("spring.application.name")
    private String appName;
    
    public SwaggerService(EurekaClient eurekaClient, RestTemplate restTemplate) {
		this.eurekaClient = eurekaClient;
		this.restTemplate = restTemplate;
	}

	public List<SwaggerResource> getSwaggerResources() {
		
		logger.info("Fetching registered applications from Eureka");
		
        List<SwaggerResource> resources = new ArrayList<>();
        eurekaClient.getApplications().getRegisteredApplications().forEach(app -> {
        	
            if (!app.getName().equalsIgnoreCase(appName /* "GR-CONF-SWAGGER-AGGREGATOR" */)) {
            	
                app.getInstances().forEach(instance -> {
					String[] possibleEndpoints = {
							String.format("http://%s:%s/v3/api-docs", instance.getHostName(), instance.getPort()),
							String.format("http://%s:%s/v2/api-docs", instance.getHostName(), instance.getPort())
					};
					
					for (String swaggerUrl : possibleEndpoints) {
					    try {
					        String swaggerJson = restTemplate.getForObject(swaggerUrl, String.class);
					        if (swaggerJson != null) {
					            resources.add(new SwaggerResource(app.getName(), swaggerUrl));
					            logger.info("Successfully retrieved Swagger JSON for service: {} at {}", app.getName(), swaggerUrl);
					            break;
					        }
					    } catch (Exception e) {
					        logger.error("Failed to retrieve Swagger JSON for service: {} at {}. Error: {}", 
					            app.getName(), swaggerUrl, e.getMessage());
					    }
					}
					
                });
            }
        });
        return resources;
    }

    public static class SwaggerResource {
        private final String name;
        private final String url;

        public SwaggerResource(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
