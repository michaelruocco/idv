package uk.co.mruoc.idv.identity.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.service.LoadIdentityRequest.LoadIdentityRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LoadIdentityRequestTest {

    private final LoadIdentityRequestBuilder builder = LoadIdentityRequest.builder();

    @Test
    void shouldReturnAlias() {
        final Alias alias = mock(Alias.class);

        final LoadIdentityRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

}
