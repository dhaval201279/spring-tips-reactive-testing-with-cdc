package com.its.springtipsreactivetestingproducer;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ReservationPojoTest {

    @Test
    public void create() throws Exception {
        Reservation r = new Reservation(null, "Shruti");
        Assert.assertNull(r.getId());
        Assert.assertThat(r.getName(), Matchers.equalTo("Shruti"));
        Assertions.assertThat(r.getName()).isEqualToIgnoringCase("Shruti");
    }
}