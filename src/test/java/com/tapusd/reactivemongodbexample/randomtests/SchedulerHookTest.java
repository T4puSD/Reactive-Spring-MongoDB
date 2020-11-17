package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;




import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

class SchedulerHookTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerHookTest.class);

    @Test
    void onSheduleHook(){
        AtomicInteger counter = new AtomicInteger();

        Schedulers.onScheduleHook("my hook",runnable -> () -> {
            String threadName = Thread.currentThread().getName();
            counter.incrementAndGet();
            LOGGER.info("before execution: " + threadName);
            runnable.run();
            LOGGER.info("after execution: " + threadName);
        });

        Flux<Integer> integerFlux = Flux.just(1,2,3).delayElements(Duration.ofMillis(1)).subscribeOn(Schedulers.immediate());

        StepVerifier.create(integerFlux).expectNext(1,2,3).verifyComplete();

        Assert.isTrue(counter.get()==3,"Count should be 3");

    }
}
