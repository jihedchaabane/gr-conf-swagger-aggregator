package com.chj.gr.config;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Configuration;

import com.chj.gr.service.SwaggerService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerUiConfig {
	
    private final SwaggerService swaggerService;
    private final SwaggerUiConfigProperties swaggerUiConfig;

    public SwaggerUiConfig(SwaggerService swaggerService, SwaggerUiConfigProperties swaggerUiConfig) {
		this.swaggerService = swaggerService;
		this.swaggerUiConfig = swaggerUiConfig;
	}

	@PostConstruct
    public void initializeSwaggerUrls() {
		
        Set<SwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        
        swaggerService.getSwaggerResources().forEach(resource -> {
        	
            urls.add(new SwaggerUiConfigProperties.SwaggerUrl(
                resource.getName(), resource.getUrl(), resource.getName()
            ));
        });
        swaggerUiConfig.setUrls(urls);
    }
}
