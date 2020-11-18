package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

class ControlFlowFastTest {

    @Test
    void fastTest(){
        Flux<Integer> slow = Flux.just(1, 2, 3).delayElements(Duration.ofMillis(100));
        Flux<Integer> fast = Flux.just(5, 6, 7, 8, 9).delayElements(Duration.ofMillis(2));

        Flux<Integer> first = Flux.first(slow, fast);

        StepVerifier.create(first)
                .expectNext(5,6,7,8,9)
                .verifyComplete();
    }
}
