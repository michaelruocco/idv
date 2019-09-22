package uk.co.mruoc.jsonapi;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JsonApiDataItemWithIdTest {

    private static final UUID ID = UUID.fromString("786fa43d-6bcd-4a0c-ab7e-21348eb77faf");
    private static final String TYPE = "fake-type";
    private static final String ATTRIBUTES = "fake-attributes";

    private final JsonApiDataItemWithId<Object> item = new JsonApiDataItemWithId<>(ID, TYPE, ATTRIBUTES);

    @Test
    void shouldReturnId() {
        assertThat(item.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnType() {
        assertThat(item.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldReturnAttributes() {
        assertThat(item.getAttributes()).isEqualTo(ATTRIBUTES);
    }

}
