package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

class HooksOnOperatorDebugTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HooksOnOperatorDebugTest.class);
    @Test
    void hooksOnOperatorDebugTest(){
        Hooks.onOperatorDebug();

        var stackTrace = new AtomicReference<String>();
        var errorFlux = Flux.error(IllegalArgumentException::new)
                .checkpoint()
                .delayElements(Duration.ofMillis(1));

        StepVerifier.create(errorFlux)
                .expectErrorMatches(ex ->{
                    stackTrace.set(stackTraceToString(ex));
                    return ex instanceof IllegalArgumentException;
                }).verify();

        LOGGER.info(stackTrace.get());
    }

    private static String stackTraceToString(Throwable throwable) {
        try (var sw = new StringWriter(); var pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
        catch (Exception ioEx) {
            throw new RuntimeException(ioEx);
        }
    }

}
