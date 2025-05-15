package com.chj.gr.config.resttemplate;

import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	/**
		@TODO din't work!
		javax:
		  net:
		    ssl:
		      trustStore: classpath:ms1-truststore.jks
		      trustStorePassword: jihed1234
	      
	   Ou configurez programmatiquement RestTemplate pour utiliser le truststore.
	*/
	
	/**
    	@LoadBalanced
    		==> l'appel avec [https/https]://MON_SERVICE_APP_NAME
    		Il faut que : eureka.instance.preferIpAddress: true
    		
    		https:
		    		eureka:
					  instance:
					    secure-port: 8441  # Advertise HTTPS port
					    secure-port-enabled: true
					    non-secure-port-enabled: false
    */
    @Bean("restTemplateSsl")
    @LoadBalanced
    public RestTemplate restTemplateSsl() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(getClass().getResourceAsStream("/ms1-truststore.jks"), "jihed1234".toCharArray());

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(trustStore, null)
                .build();

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
    
    @Bean("restTemplate")
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}