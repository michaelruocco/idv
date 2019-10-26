package uk.co.mruoc.idv.identity.jsonapi.error;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.jsonapi.error.JsonApiErrorItem;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundErrorItemTest {

    private static final String DETAIL = "my detail";

    private final JsonApiErrorItem error = new IdentityNotFoundErrorItem(DETAIL);

    @Test
    void shouldReturnRandomId() {
        assertThat(error.getId()).isNotNull();
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Identity Not Found");
    }

    @Test
    void shouldReturnDetail() {
        assertThat(error.getDetail()).isEqualTo(DETAIL);
    }

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(404);
    }

}
