package uk.co.mruoc.idv.identity.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.service.FakeIdGenerator;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.model.IdvId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DefaultIdentityServiceTest {

    private final FakeIdGenerator idGenerator = new FakeIdGenerator();

    private final IdentityService service = DefaultIdentityService.builder()
            .idGenerator(idGenerator)
            .build();

    @Test
    void shouldReturnIdentityWithGeneratedIdvIdProvidedAlias() {
        final UUID idvIdValue = UUID.randomUUID();
        idGenerator.setIdToGenerate(idvIdValue);
        final Alias providedAlias = mock(Alias.class);
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactly(new IdvId(idvIdValue), providedAlias);
    }

    private static UpsertIdentityRequest buildUpsertRequest(final Alias providedAlias) {
        final Channel channel = mock(Channel.class);
        return UpsertIdentityRequest.builder()
                .channel(channel)
                .providedAlias(providedAlias)
                .build();
    }

}
