package uk.co.idv.api.verificationcontext.error;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.jsonapi.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class AliasNotSupportedErrorTest {

    private static final String DETAIL = "my detail";

    private final ApiError error = new AliasNotSupportedError(DETAIL);

    @Test
    void shouldReturnRandomId() {
        assertThat(error.getId()).isNotNull();
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Alias Not Supported");
    }

    @Test
    void shouldReturnDetail() {
        assertThat(error.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(400);
    }

}
