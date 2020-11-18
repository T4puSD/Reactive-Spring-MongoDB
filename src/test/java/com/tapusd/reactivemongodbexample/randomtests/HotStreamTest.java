package com.tapusd.reactivemongodbexample.randomtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.emitter.Emitter;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;

class HotStreamTest {

    @Test
    void hotStream(){
        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        EmitterProcessor<Integer> emitter = EmitterProcessor.create(2);
        FluxSink<Integer> sink = emitter.sink();

        emitter.subscribe(first::add);
        sink.next(1);
        sink.next(2);
        emitter.subscribe(second::add);
        sink.next(3);
        sink.complete();

        Assertions.assertTrue(first.size() > second.size(), "Fist should consume more data than second!");
    }
}
