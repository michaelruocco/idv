package uk.co.idv.api.verificationcontext.error;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.jsonapi.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationContextExpiredErrorTest {

    private static final String DETAIL = "my detail";

    private final ApiError error = new VerificationContextExpiredError(DETAIL);

    @Test
    void shouldReturnRandomId() {
        assertThat(error.getId()).isNotNull();
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Verification Context Expired");
    }

    @Test
    void shouldReturnDetail() {
        assertThat(error.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(410);
    }

}
