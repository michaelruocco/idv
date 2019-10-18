package uk.co.mruoc.idv.verificationcontext.jsonapi.error;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidJsonRequestErrorItemTest {

    private static final String DETAIL = "my detail";

    private final JsonApiErrorItem error = new InvalidJsonRequestErrorItem(DETAIL);

    @Test
    void shouldReturnRandomId() {
        assertThat(error.getId()).isNotNull();
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Invalid JSON Request");
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
