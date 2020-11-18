package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

class ControlFlowTimeOutTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlFlowRetryTest.class);

    @Test
    void timeOutTest(){
        Flux<Integer> integerFlux = Flux.just(1,2,3)
                .delayElements(Duration.ofMillis(1000))
                .timeout(Duration.ofMillis(500))
                .onErrorResume(this::given);

        StepVerifier.create(integerFlux)
                .expectNext(0)
                .verifyComplete();
    }

    private Flux<Integer> given(Throwable t){
        Assertions.assertTrue(t instanceof TimeoutException, "This should be a " + TimeoutException.class.getName());
        return Flux.just(0);
    }
}
