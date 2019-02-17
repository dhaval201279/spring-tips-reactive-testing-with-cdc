package com.its.springtipsreactivetestingproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class SpringTipsReactiveTestingProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTipsReactiveTestingProducerApplication.class, args);
	}

}

class ReservationRestConfiguration {
	@Bean
	RouterFunction<ServerResponse> routes(ReservationRepository reservationRepository) {
		return route(GET("/reservations"), serverRequest ->
				ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(reservationRepository.findAll(), Reservation.class));
	};
}

