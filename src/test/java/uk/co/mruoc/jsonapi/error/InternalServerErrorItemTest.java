package uk.co.mruoc.jsonapi.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InternalServerErrorItemTest {

    private static final String DETAIL = "my detail";
    private static final int STATUS = 500;

    private final JsonApiErrorItem item = new InternalServerErrorItem(DETAIL);

    @Test
    void shouldGenerateRandomIdIfNotProvided() {
        assertThat(item.getId()).isNotNull();
    }

    @Test
    void shouldReturnStatus() {
        assertThat(item.getStatus()).isEqualTo(STATUS);
    }

}
