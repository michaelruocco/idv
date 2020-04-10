package uk.co.idv.domain.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.usecases.identity.LoadIdentityRequest.LoadIdentityRequestBuilder;

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
