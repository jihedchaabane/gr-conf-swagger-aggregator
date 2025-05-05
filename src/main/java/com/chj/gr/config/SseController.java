package com.chj.gr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server-Sent Events (SSE):
 * 		pour rafrachissement de la page swagger côté client une fois l'actualisation depuis eureka est récu.
 */
@RestController
public class SseController {
    private static final Logger logger = LoggerFactory.getLogger(SseController.class);
    private final Set<SseEmitter> emitters = ConcurrentHashMap.newKeySet();

    @GetMapping(value = "/sse/eureka-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToEurekaUpdates() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        logger.info("New SSE connection established for eureka-updates");

        emitter.onCompletion(() -> {
            emitters.remove(emitter);
            logger.info("SSE connection completed");
        });
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            logger.info("SSE connection timed out");
        });
        emitter.onError((e) -> {
            emitters.remove(emitter);
            logger.error("SSE connection error: {}", e.getMessage());
        });

        return emitter;
    }

    public void sendEurekaUpdateEvent() {
        logger.info("Sending Eureka update event to {} clients", emitters.size());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                    .name("eureka-update")
                    .data("Eureka registry updated"));
                logger.debug("Sent Eureka update event to emitter");
            } catch (IOException e) {
                logger.error("Failed to send Eureka update event: {}", e.getMessage());
                emitters.remove(emitter);
            }
        }
    }
}
