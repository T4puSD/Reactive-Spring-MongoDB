package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class RandomTests {
    @Test
    void test1(){
        Flux<String> charFlux = Flux.just("a", "b", "c").map(String::toUpperCase);
        StepVerifier.create(charFlux)
                .expectNext("A","B","c")
                .verifyComplete();
    }
}
