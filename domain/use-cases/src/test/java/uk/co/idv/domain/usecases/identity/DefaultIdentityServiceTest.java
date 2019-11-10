package uk.co.idv.domain.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.FakeChannel;
import uk.co.idv.domain.usecases.util.FakeIdGenerator;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.IdentityService.IdentityNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
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
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        given(dao.load(providedAlias)).willReturn(Optional.empty());

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactlyInAnyOrder(
                new IdvId(VALUE),
                providedAlias
        );
    }

    @Test
    void shouldReturnNewIdentityWithGeneratedIdvIdAndAdditionalAliasIfIdentityNotFoundAndAliasValueEndsWithTwo() {
        final Alias providedAlias = AliasesMother.creditCardNumber("4929992222222222");
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        given(dao.load(providedAlias)).willReturn(Optional.empty());

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactlyInAnyOrder(
                new IdvId(VALUE),
                providedAlias,
                AliasesMother.creditCardNumber("4929992222222223")
        );
    }

    @Test
    void shouldReturnNewIdentityWithProvidedAliasIfIdentityNotFound() {
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        given(dao.load(providedAlias)).willReturn(Optional.empty());

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactly(new IdvId(VALUE), providedAlias);
    }

    @Test
    void shouldReturnExistingIdentityIfIdentityFound() {
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final UpsertIdentityRequest request = buildUpsertRequest(providedAlias);
        final Identity existingIdentity = mock(Identity.class);
        given(dao.load(providedAlias)).willReturn(Optional.of(existingIdentity));

        final Identity identity = service.upsert(request);

        assertThat(identity).isEqualTo(existingIdentity);
    }

    @Test
    void shouldThrowExceptionIfIdentityNotFound() {
        final Alias alias = AliasesMother.creditCardNumber();
        final LoadIdentityRequest request = buildLoadRequest(alias);
        given(dao.load(alias)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> service.load(request));

        assertThat(error)
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(String.format("aliasType: %s aliasValue: %s", alias.getType(), alias.getValue()));
    }

    @Test
    void shouldLoadIdentity() {
        final Alias alias = AliasesMother.creditCardNumber();
        final LoadIdentityRequest request = buildLoadRequest(alias);
        final Identity existingIdentity = mock(Identity.class);
        given(dao.load(alias)).willReturn(Optional.of(existingIdentity));

        final Identity identity = service.load(request);

        assertThat(identity).isEqualTo(existingIdentity);
    }

    private static UpsertIdentityRequest buildUpsertRequest(final Alias providedAlias) {
        final Channel channel = new FakeChannel();
        return UpsertIdentityRequest.builder()
                .channel(channel)
                .providedAlias(providedAlias)
                .build();
    }

    private static LoadIdentityRequest buildLoadRequest(final Alias providedAlias) {
        return LoadIdentityRequest.builder()
                .alias(providedAlias)
                .build();
    }

}
