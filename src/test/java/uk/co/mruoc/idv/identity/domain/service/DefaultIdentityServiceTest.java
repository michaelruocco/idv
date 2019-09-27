package uk.co.mruoc.idv.identity.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.service.FakeIdGenerator;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.model.IdvId;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultIdentityServiceTest {

    private static final UUID VALUE = UUID.randomUUID();

    private final IdentityDao dao = mock(IdentityDao.class);
    private final FakeIdGenerator idGenerator = new FakeIdGenerator(VALUE);

    private final IdentityService service = DefaultIdentityService.builder()
            .idGenerator(idGenerator)
            .dao(dao)
            .build();

    @Test
    void shouldReturnNewIdentityWithGeneratedIdvIdIfIdentityNotFound() {
        final Alias providedAlias = mock(Alias.class);
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        given(dao.load(providedAlias)).willReturn(Optional.empty());

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).contains(new IdvId(VALUE));
    }

    @Test
    void shouldReturnNewIdentityWithProvidedAliasIfIdentityNotFound() {
        final Alias providedAlias = mock(Alias.class);
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        given(dao.load(providedAlias)).willReturn(Optional.empty());

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactly(new IdvId(VALUE), providedAlias);
    }

    @Test
    void shouldReturnExistingIdentityIfIdentityFound() {
        final Alias providedAlias = mock(Alias.class);
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        final Identity existingIdentity = mock(Identity.class);
        given(dao.load(providedAlias)).willReturn(Optional.of(existingIdentity));

        final Identity identity = service.upsert(request);

        assertThat(identity).isEqualTo(existingIdentity);
    }

    private static UpsertIdentityRequest buildUpsertRequest(final Alias providedAlias) {
        final Channel channel = mock(Channel.class);
        return UpsertIdentityRequest.builder()
                .channel(channel)
                .providedAlias(providedAlias)
                .build();
    }

}
