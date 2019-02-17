package com.its.springtipsreactivetestingproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest
@Import(ReservationRestConfiguration.class)
@RunWith(SpringRunner.class)
public class ReservationRestTest {

    @MockBean
    private ReservationRepository mockReservationRepository;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void getAllReservations() throws Exception {
        Mockito
            .when(mockReservationRepository.findAll())
            .thenReturn(
                Flux.just(new Reservation("1", "A"), new Reservation("2", "B"))
            );

        webTestClient
            .get()
            .uri("http://localhost:8080/reservations")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBody()
            .jsonPath("@.[0].id").isEqualTo("1")
            .jsonPath("@.[0].name").isEqualTo("A")
            .jsonPath("@.[1].id").isEqualTo("2")
            .jsonPath("@.[1].name").isEqualTo("B")
            ;
    }
}
