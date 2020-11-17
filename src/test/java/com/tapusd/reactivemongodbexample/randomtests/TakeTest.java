package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

class TakeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TakeTest.class);

    @Test
    void take(){
        long count = 10;
        Flux<Integer> take = range().take(count);
        LOGGER.info("Take flux has: " + take);
        take.toIterable().forEach(System.out::println);
        StepVerifier.create(take).expectNextCount(count).verifyComplete();
    }

    @Test
    void takeUntil(){
       long count = 50;
       Flux<Integer> takeUntil = range().takeUntil(num-> num >= (count-1));
       LOGGER.info("Take Until Flux has: ");
       takeUntil.toIterable().forEach(System.out::println);
       StepVerifier.create(takeUntil).expectNextCount(count).verifyComplete();
    }

    private Flux<Integer> range() {
       return Flux.range(0,1000);
    }
}
