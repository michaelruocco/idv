package uk.co.mruoc.jsonapi;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonApiDataItemTest {

    private static final String TYPE = "fake-type";
    private static final String ATTRIBUTES = "fake-attributes";

    private final JsonApiDataItem<Object> item = new JsonApiDataItem<>(TYPE, ATTRIBUTES);

    @Test
    void shouldReturnType() {
        assertThat(item.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldReturnAttributes() {
        assertThat(item.getAttributes()).isEqualTo(ATTRIBUTES);
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(JsonApiDataItem.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
