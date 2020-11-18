package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

class ContextTest {
    private static final Logger log = LoggerFactory.getLogger(ContextTest.class);

    @Test
    void context() throws Exception {
        var observedContextValues = new ConcurrentHashMap<String, AtomicInteger>();
        var max = 3;
        var key = "key1";
        var cdl = new CountDownLatch(max);
        Context context = Context.of(key, "value1");
        Flux<Integer> just = Flux//
                .range(0, max)//
                .delayElements(Duration.ofMillis(1))//
                .doOnEach((Signal<Integer> integerSignal) -> {
                    Context currentContext = integerSignal.getContext();
                    if (integerSignal.getType().equals(SignalType.ON_NEXT)) {
                        String key1 = currentContext.get(key);
                        Assertions.assertNotNull(key1);
                        Assertions.assertEquals(key1, "value1");
                        observedContextValues
                                .computeIfAbsent("key1", k -> new AtomicInteger(0))
                                .incrementAndGet();
                    }
                })//
                .subscriberContext(context);
        just.subscribe(integer -> {
            log.info("integer: " + integer);
            cdl.countDown();
        });
        cdl.await();

        Assertions.assertEquals(observedContextValues.get(key).get(),max);
    }

}
