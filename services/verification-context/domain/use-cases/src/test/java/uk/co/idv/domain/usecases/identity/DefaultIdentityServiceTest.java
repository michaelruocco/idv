package uk.co.idv.domain.usecases.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.usecases.identity.data.IdentityDataResponse;
import uk.co.idv.domain.usecases.identity.data.IdentityDataResponseMother;
import uk.co.idv.domain.usecases.identity.data.IdentityDataService;
import uk.co.idv.domain.usecases.util.id.FakeIdGenerator;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.IdentityService.IdentityNotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultIdentityServiceTest {

    private static final UUID VALUE = UUID.randomUUID();

    private final FakeIdGenerator idGenerator = new FakeIdGenerator(VALUE);
    private final IdentityDataService dataService = mock(IdentityDataService.class);
    private final IdentityDao dao = mock(IdentityDao.class);

    private final IdentityService service = DefaultIdentityService.builder()
            .idGenerator(idGenerator)
            .dataService(dataService)
            .dao(dao)
            .build();

    @Test
    void shouldReturnNewIdentityWithGeneratedIdvIdIfIdentityNotFound() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        given(dao.load(request.getProvidedAlias())).willReturn(Optional.empty());
        final IdentityDataResponse dataResponse = IdentityDataResponseMother.empty();
        given(dataService.load(request)).willReturn(dataResponse);

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactlyInAnyOrder(new IdvId(VALUE));
    }

    @Test
    void shouldReturnNewIdentityWithLoadedAliasesIfIdentityNotFound() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        given(dao.load(request.getProvidedAlias())).willReturn(Optional.empty());
        final IdentityDataResponse dataResponse = IdentityDataResponseMother.withAlias(request.getProvidedAlias());
        given(dataService.load(request)).willReturn(dataResponse);

        final Identity identity = service.upsert(request);

        final Aliases aliases = identity.getAliases();
        assertThat(aliases).containsExactly(request.getProvidedAlias(), new IdvId(VALUE));
    }

    @Test
    void shouldReturnNewIdentityWithLoadedPhoneNumbersIfIdentityNotFound() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        given(dao.load(request.getProvidedAlias())).willReturn(Optional.empty());
        final IdentityDataResponse dataResponse = IdentityDataResponseMother.empty();
        given(dataService.load(request)).willReturn(dataResponse);

        final Identity identity = service.upsert(request);

        final PhoneNumbers phoneNumbers = identity.getPhoneNumbers();
        assertThat(phoneNumbers).isEqualTo(dataResponse.getPhoneNumbers());
    }

    @Test
    void shouldReturnNewIdentityWithLoadedAccountsIfIdentityNotFound() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        given(dao.load(request.getProvidedAlias())).willReturn(Optional.empty());
        final IdentityDataResponse dataResponse = IdentityDataResponseMother.empty();
        given(dataService.load(request)).willReturn(dataResponse);

        final Identity identity = service.upsert(request);

        final Collection<Account> accounts = identity.getAccounts();
        assertThat(accounts).isEqualTo(dataResponse.getAccounts());
    }

    @Test
    void shouldReturnExistingIdentityIfIdentityFound() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        final Identity existingIdentity = mock(Identity.class);
        given(dao.load(request.getProvidedAlias())).willReturn(Optional.of(existingIdentity));

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

    private static LoadIdentityRequest buildLoadRequest(final Alias providedAlias) {
        return LoadIdentityRequest.builder()
                .alias(providedAlias)
                .build();
    }

}
