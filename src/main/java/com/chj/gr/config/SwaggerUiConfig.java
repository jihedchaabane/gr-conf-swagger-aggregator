//package com.chj.gr.config;
//
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//
//import org.springdoc.core.SwaggerUiConfigProperties;
//import org.springframework.context.annotation.Configuration;
//
//import com.chj.gr.service.SwaggerService;
//import com.chj.gr.service.SwaggerService.SwaggerResource;
//
//@Configuration
//public class SwaggerUiConfig {
//
//	private final SwaggerService swaggerService;
//	private final SwaggerUiConfigProperties swaggerUiConfig;
//
//	public SwaggerUiConfig(SwaggerService swaggerService, SwaggerUiConfigProperties swaggerUiConfig) {
//		this.swaggerService = swaggerService;
//		this.swaggerUiConfig = swaggerUiConfig;
//	}
//
//	@PostConstruct
//	public void initializeSwaggerUrls() {
//
//		Set<SwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
//
//		swaggerService.getSwaggerResources()
//				.stream()
//				.sorted(Comparator.comparing(SwaggerResource::getName))
//				.forEach(resource -> {
//					urls.add(new SwaggerUiConfigProperties.SwaggerUrl(resource.getName(), resource.getUrl(), resource.getName()));
//				});
//		swaggerUiConfig.setUrls(urls);
//	}
//}
