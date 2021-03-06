package uk.co.idv.domain.entities.verificationcontext.method.eligibility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoMobileApplicationTest {

    private final Ineligible ineligible = new NoMobileApplication();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains("no mobile application");
    }

}
