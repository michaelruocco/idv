package uk.co.idv.domain.entities.verificationcontext.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoEligibleCardsTest {

    private final Ineligible ineligible = new NoEligibleCards();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains("no eligible cards found");
    }

}
