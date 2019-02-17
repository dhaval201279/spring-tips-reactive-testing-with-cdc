package com.its.springtipsreactivetestingproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Predicate;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ReservationDocumentTest {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void persist() throws Exception{
        Flux<Reservation> saved = Flux
            .just(new Reservation(null, "one"), new Reservation(null, "two"))
            .flatMap(r -> this.reactiveMongoTemplate.save(r));

        Flux<Reservation> interaction = reactiveMongoTemplate
            .dropCollection(Reservation.class)
            .thenMany(saved)
            .thenMany(reactiveMongoTemplate.findAll(Reservation.class));

        Predicate<Reservation> reservationPredicate = r ->
            StringUtils.hasText(r.getId()) && (
            r.getName().equalsIgnoreCase("one") ||
            r.getName().equalsIgnoreCase("two"));

        StepVerifier
            .create(interaction)
            .expectNextMatches(reservationPredicate)
            .expectNextMatches(reservationPredicate)
            .verifyComplete();

        /**
         * Using StepVerifier with time as dimension
         * */
        /*StepVerifier
            .withVirtualTime(() -> Flux.just("A", "B").delayElements(Duration.ofSeconds(1)))
            .thenAwait(Duration.ofSeconds(10))
            .expectNextCount(2)
            .verifyComplete();*/
    }
}
