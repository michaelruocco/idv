package uk.co.idv.api.verificationcontext.error;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.jsonapi.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodInSequenceErrorTest {

    private static final String DETAIL = "my detail";

    private final ApiError error = new NotNextMethodInSequenceError(DETAIL);

    @Test
    void shouldReturnRandomId() {
        assertThat(error.getId()).isNotNull();
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Not Next Method in Sequence");
    }

    @Test
    void shouldReturnDetail() {
        assertThat(error.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

}
