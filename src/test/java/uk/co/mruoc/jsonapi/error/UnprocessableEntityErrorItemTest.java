package uk.co.mruoc.jsonapi.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnprocessableEntityErrorItemTest {

    private static final String TITLE = "My Title";
    private static final String DETAIL = "my detail";
    private static final int STATUS = 422;

    private final JsonApiErrorItem item = new UnprocessableEntityErrorItem(TITLE, DETAIL);

    @Test
    void shouldGenerateRandomIdIfNotProvided() {
        assertThat(item.getId()).isNotNull();
    }

    @Test
    void shouldReturnStatus() {
        assertThat(item.getStatus()).isEqualTo(STATUS);
    }

}
