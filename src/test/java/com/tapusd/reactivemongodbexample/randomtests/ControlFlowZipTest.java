package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ControlFlowZipTest {
    @Test
    void zipTest(){
        Flux<Integer> integerFlux = Flux.just(1, 2, 3);
        Flux<String> stringFlux = Flux.just("a","b","c");

        Flux<String> zip = Flux.zip(integerFlux, stringFlux)
                .map(objects -> this.from(objects.getT1(), objects.getT2()));

        StepVerifier.create(zip)
                .expectNext("1:a","2:b","3:c")
                .verifyComplete();
    }

    String from(Integer i, String s){
        return i + ":" + s;
    }
}
