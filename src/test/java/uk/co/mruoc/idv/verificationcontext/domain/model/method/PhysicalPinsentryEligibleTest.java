package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryEligibleTest {

    private final PinsentryFunction function = PinsentryFunction.RESPOND;
    private final Collection<CardNumber> cardNumbers = Collections.singleton(new CardNumber("1234876543211234"));

    private final PhysicalPinsentryEligible method = PhysicalPinsentryEligible.builder()
            .function(function)
            .cardNumbers(cardNumbers)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("physical-pinsentry");
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnFunction() {
        assertThat(method.getFunction()).isEqualTo(function);
    }

    @Test
    void shouldReturnCardNumbers() {
        assertThat(method.getCardNumbers()).containsExactlyElementsOf(cardNumbers);
    }

}
