package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleCardsTest {

    private final Ineligible ineligible = new NoEligibleCards();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.reason()).contains("no eligible cards found");
    }

}
