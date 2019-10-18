package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.service.LoadContextRequest.LoadContextRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoadContextRequestTest {

    private final LoadContextRequestBuilder builder = LoadContextRequest.builder();

    @Test
    void shouldReturnChannel() {
        final UUID id = UUID.randomUUID();

        final LoadContextRequest request = builder.id(id).build();

        assertThat(request.getId()).isEqualTo(id);
    }

}