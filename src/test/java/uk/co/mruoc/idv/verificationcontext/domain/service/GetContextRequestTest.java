package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.service.GetContextRequest.GetContextRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GetContextRequestTest {

    private final GetContextRequestBuilder builder = GetContextRequest.builder();

    @Test
    void shouldReturnChannel() {
        final UUID id = UUID.randomUUID();

        final GetContextRequest request = builder.id(id).build();

        assertThat(request.getId()).isEqualTo(id);
    }

}
