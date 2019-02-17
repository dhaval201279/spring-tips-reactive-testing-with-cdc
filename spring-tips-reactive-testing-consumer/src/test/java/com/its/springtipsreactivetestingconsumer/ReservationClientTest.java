package com.its.springtipsreactivetestingconsumer;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubRunner;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

/***
 * In order to run this test we need to have our producer endpoint up and running. this might not be
 * realistically possible. Hence we can use wiremock
 */
//@AutoConfigureWireMock(port = 8080)
@AutoConfigureStubRunner(ids = "com.its:spring-tips-reactive-testing-producer:+:8080", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationClientTest {

	@Autowired
	private ReservationClient reservationClient;

	@Test
	public void getAllReservations() throws Exception {
		/** When we use spring cloud contract testing we dont need to wiremock
		 * hence commented and alternative added implementation for consumer driven contract tests
		*
		WireMock
			.stubFor(WireMock.any(WireMock.urlMatching("/reservations"))
				.willReturn(WireMock
								.aResponse()
								.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
								.withStatus(HttpStatus.OK.value())
								.withBody("[ {\"id\" : \"1\", \"name\" : \"Yatharth\"}, " +
									"{\"id\" : \"2\", \"name\" : \"Shruti\"} ]")
				)
			);*/

		Flux<Reservation> allReservations = reservationClient.getAllReservations();

		Predicate <Reservation> reservationPredicate = reservation ->
				(reservation.getName().equalsIgnoreCase("Yatharth") ||
				reservation.getName().equalsIgnoreCase("Shruti")) && StringUtils.hasText(reservation.getId());

		StepVerifier
			.create(allReservations)
			.expectNextMatches(reservationPredicate)
			.expectNextMatches(reservationPredicate)
			.verifyComplete();
	}

}

