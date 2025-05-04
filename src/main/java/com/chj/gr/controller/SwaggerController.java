//package com.chj.gr.controller;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springdoc.core.SwaggerUiConfigProperties;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.chj.gr.service.SwaggerService;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequiredArgsConstructor
//public class SwaggerController {
//	
//	private static final Logger logger = LoggerFactory.getLogger(SwaggerController.class);
//    private final SwaggerService swaggerService;
//    private final SwaggerUiConfigProperties swaggerUiConfig;
//
//	public SwaggerController(SwaggerService swaggerService, SwaggerUiConfigProperties swaggerUiConfig) {
//		this.swaggerService = swaggerService;
//		this.swaggerUiConfig = swaggerUiConfig;
//	}
//
//	@GetMapping("/swagger-resources")
//    public Map<String, SwaggerUiConfigProperties.SwaggerUrl> getSwaggerResources() {
//		
//		logger.info("Fetching Swagger resources");
//        Map<String, SwaggerUiConfigProperties.SwaggerUrl> urlsMap = new HashMap<>();
//        Set<SwaggerUiConfigProperties.SwaggerUrl> urlsSet = new HashSet<>();
//        
//        swaggerService.getSwaggerResources().forEach(resource -> {
//        	
//            SwaggerUiConfigProperties.SwaggerUrl swaggerUrl = new SwaggerUiConfigProperties.SwaggerUrl(
//                resource.getName(), resource.getUrl(), resource.getName()
//            );
//            
//            urlsMap.put(resource.getName(), swaggerUrl);
//            urlsSet.add(swaggerUrl);
//            logger.info("Added Swagger resource: {} -> {}", resource.getName(), resource.getUrl());
//        });
//        
//        swaggerUiConfig.setUrls(urlsSet);
//        logger.info("Configured {} Swagger URLs", urlsSet.size());
//        return urlsMap;
//    }
//}
