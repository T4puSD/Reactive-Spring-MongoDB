package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class OnErrorTest {
    private final Flux<Integer> errorFlux = Flux.just(1,2,3).flatMap(integer -> {
        if(integer == 2){
            return Flux.error(IllegalArgumentException::new);
        } else {
            return Flux.just(integer);
        }
    });

    @Test
    void onErrorReturnTest(){
        Flux<Integer> integerFlux = errorFlux.onErrorReturn(0);

        StepVerifier.create(integerFlux)
                .expectNext(1,0)
                .verifyComplete();
    }

    @Test
    void onErrorResumeTest(){
        Flux<Integer> integerFlux = errorFlux.onErrorResume(IllegalArgumentException.class, e -> {
            return Flux.just(3, 2, 1, 0);
        });

        StepVerifier.create(integerFlux)
                .expectNext(1,3,2,1,0)
                .verifyComplete();
    }
}
