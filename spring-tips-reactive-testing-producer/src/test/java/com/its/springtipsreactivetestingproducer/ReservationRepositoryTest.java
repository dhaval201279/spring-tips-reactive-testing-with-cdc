package com.its.springtipsreactivetestingproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;


    @Test
    public void findAllByReservationName() throws Exception {
        Flux<Reservation> flux = reservationRepository
            .deleteAll()
            .thenMany(Flux
                    .just("A", "B", "C", "D", "C")
                    .flatMap(name -> reservationRepository.save(new Reservation(null, name)))
            )
            .thenMany(reservationRepository.findByName("C"));

        StepVerifier
            .create(flux)
            .expectNextCount(2)
            .verifyComplete();
    }
}
