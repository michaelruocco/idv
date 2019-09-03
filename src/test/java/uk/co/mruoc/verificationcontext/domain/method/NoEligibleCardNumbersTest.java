package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleCardNumbersTest {

    private final Ineligible ineligible = new NoEligibleCardNumbers();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.reason()).contains("no eligible card numbers found");
    }

}
