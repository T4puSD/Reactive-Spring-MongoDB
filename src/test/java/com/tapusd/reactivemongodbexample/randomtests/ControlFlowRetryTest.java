package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicBoolean;

class ControlFlowRetryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlFlowRetryTest.class);

    @Test
    void retry(){
        AtomicBoolean erroed = new AtomicBoolean();
        Flux<String> producer = Flux.create(sink ->{
            if(!erroed.get()){
                erroed.set(true);
                sink.error(new RuntimeException("Error Occurred!"));
                LOGGER.info("returning a {} !",RuntimeException.class.getName());
            } else {
                LOGGER.info("Error already occurred. So here the expected value!");
                sink.next("Hello!");
            }
            sink.complete();
        });

        Flux<String> retryOnError = producer.retry();
        StepVerifier.create(retryOnError)
                .expectNext("Hello!")
                .verifyComplete();
    } 
}
