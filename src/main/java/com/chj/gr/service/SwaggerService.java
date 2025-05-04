package com.chj.gr.service;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SwaggerService {
	
    private static final Logger logger = LoggerFactory.getLogger(SwaggerService.class);
    
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;

    
    public SwaggerService(EurekaClient eurekaClient, RestTemplate restTemplate) {
		this.eurekaClient = eurekaClient;
		this.restTemplate = restTemplate;
	}

	public List<SwaggerResource> getSwaggerResources() {
		
		logger.info("Fetching registered applications from Eureka");
		
        List<SwaggerResource> resources = new ArrayList<>();
        eurekaClient.getApplications().getRegisteredApplications().forEach(app -> {
        	
            if (!app.getName().equalsIgnoreCase("GR-CONF-SWAGGER-AGGREGATOR")) {
            	
                app.getInstances().forEach(instance -> {
                    String swaggerUrl = String.format("http://%s:%s/v3/api-docs", instance.getHostName(), instance.getPort());
                    try {
                        // Vérifier si l'endpoint Swagger est accessible
                        String swaggerJson = restTemplate.getForObject(swaggerUrl, String.class);
                        
                        if (swaggerJson != null) {
                            resources.add(new SwaggerResource(app.getName(), swaggerUrl));
                            logger.info("Successfully retrieved Swagger JSON for service: {} at {}", app.getName(), swaggerUrl);
                        }
                    } catch (Exception e) {
                        logger.error("Failed to retrieve Swagger JSON for service: {} at {}. Error: {}", 
                            app.getName(), swaggerUrl, e.getMessage());
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
