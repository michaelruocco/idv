package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.LoadLockoutStateRequest.LoadLockoutStateRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LoadLockoutStateRequestTest {

    private final LoadLockoutStateRequestBuilder builder = LoadLockoutStateRequest.builder();

    @Test
    void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();

        final LoadLockoutStateRequest request = builder.idvId(idvId).build();

        assertThat(request.getIdvId()).isEqualTo(idvId);
    }

}
