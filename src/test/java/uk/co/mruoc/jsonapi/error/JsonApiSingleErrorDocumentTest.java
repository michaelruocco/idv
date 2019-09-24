package uk.co.mruoc.jsonapi.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonApiSingleErrorDocumentTest {

    private final JsonApiErrorItem error = new FakeInternalServerErrorItem();
    private final JsonApiErrorDocument document = new JsonApiSingleErrorDocument(error);

    @Test
    void shouldReturnStatusFromError() {
        assertThat(document.getStatus()).isEqualTo(error.getStatus());
    }

}
