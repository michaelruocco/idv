package uk.co.mruoc.jsonapi.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonApiMultipleErrorDocumentTest {

    @Test
    void shouldReturnStatusFromErrorsIfErrorsHaveSameStatus() {
        final JsonApiErrorItem error1 = new FakeInternalServerErrorItem();
        final JsonApiErrorItem error2 = new FakeInternalServerErrorItem();

        final JsonApiErrorDocument document = new JsonApiMultipleErrorDocument(error1, error2);

        assertThat(document.getStatus()).isEqualTo(error1.getStatus());
        assertThat(document.getStatus()).isEqualTo(error2.getStatus());
    }

    @Test
    void shouldReturnMultiStatusIfErrorsDoNotHaveSameStatus() {
        final JsonApiErrorItem error1 = new FakeNotFoundErrorItem();
        final JsonApiErrorItem error2 = new FakeBadRequestErrorItem();

        final JsonApiErrorDocument document = new JsonApiMultipleErrorDocument(error1, error2);

        final int multiStatus = 207;
        assertThat(document.getStatus()).isEqualTo(multiStatus);
    }

}
