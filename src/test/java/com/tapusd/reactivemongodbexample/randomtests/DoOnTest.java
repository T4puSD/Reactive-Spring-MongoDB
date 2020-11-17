package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

class DoOnTest {
    public static Logger LOGGER = LoggerFactory.getLogger(DoOnTest.class);
    @Test
    void doOn(){
        List<Integer> intVals = new ArrayList<>();
        Flux<Integer> source = Flux.<Integer>create(fluxSink -> {
            fluxSink.next(0);
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.next(3);
            fluxSink.complete();
        }).doOnNext(intVals::add).doOnEach(System.out::println);

        intVals.forEach(val -> LOGGER.info("val is: {}", val));
        LOGGER.info("Size of int val is: {}",intVals.size());

        StepVerifier.create(source).expectNext(0,1,2,3).verifyComplete();


    }
}
