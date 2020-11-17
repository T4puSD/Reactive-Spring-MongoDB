package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

class RandomTests {
    @Test
    void test1() {
        Flux<String> charFlux = Flux.just("a", "b", "c").map(String::toUpperCase);
        StepVerifier.create(charFlux)
                .expectNext("A","B","c")
                .verifyComplete();
    }


    @Test
    void switchMapTest() {
        Flux<String> source = Flux
                .just("r","r","re","rea","react","reactiv","reactive")
                .delayElements(Duration.ofMillis(100))
                .switchMap(this::lookUp);

        // only last element get's passed from the switch map!
        StepVerifier.create(source)
                .expectNext("reactive -> reactive")
                .verifyComplete();
        
    }

    private Flux<String> lookUp(String word) {
        return Flux.just("reactive -> " + word)
                .delayElements(Duration.ofMillis(200));
    }
}
