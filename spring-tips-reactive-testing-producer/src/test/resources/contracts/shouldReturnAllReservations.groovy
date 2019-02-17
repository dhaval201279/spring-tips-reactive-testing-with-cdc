import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

Contract.make {
    description("should return all reservations")

    request {
        url("/reservations")
        method(GET())
    }

    response {
        status(HttpStatus.OK.value())
        headers {
            contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        }
        /** One way of generating JSON response within body
        *
        * body([
                [id : 1, reservationName : "Yatharth"],
                [id : 2, reservationName: "Shruti"]
        ])
         */
        body(
        """
            [
                { "id" : "1", "name" : "Yatharth" },
                { "id" : "2", "name" : "Shruti" }
            ]
        """
        )

    }
}