package uk.co.idv.repository.dynamo.identity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.dynamo.test.DynamoDbLocalContainer;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.repository.dynamo.VerificationContextDynamoConfig;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.converter.jackson.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class DynamoIdentityDaoTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    private IdentityDao dao;

    @BeforeEach
    void setUp() {
        final VerificationContextDynamoConfig config = new VerificationContextDynamoConfig(DYNAMO_DB.buildClient());
        final ObjectMapperFactory factory = new ObjectMapperFactory(new IdentityModule());
        final JsonConverter jsonConverter = new JacksonJsonConverter(factory.build());
        dao = config.identityDao(jsonConverter);
    }

    @Test
    void shouldLoadByIdvIdAlias() {
        final Identity identity = IdentityMother.emptyDataBuilder()
                .aliases(AliasesMother.aliases())
                .build();
        dao.save(identity);

        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.idvId());

        assertThat(loadedIdentity).contains(identity);
    }

    @Test
    void shouldLoadByCreditCardNumberAlias() {
        final Identity identity = IdentityMother.emptyDataBuilder()
                .aliases(AliasesMother.aliases())
                .build();
        dao.save(identity);

        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.creditCardNumber());

        assertThat(loadedIdentity).contains(identity);
    }

    @Test
    void shouldLoadEmptyOptionalIfIdvIdAliasIsNotSaved() {
        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.randomIdvId());

        assertThat(loadedIdentity).isEmpty();
    }

    @Test
    void shouldLoadEmptyOptionalIfAliasIsNotSaved() {
        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.debitCardNumber());

        assertThat(loadedIdentity).isEmpty();
    }

    @Test
    void shouldDeleteAliasFromIdentityWhenUpdatedAndAliasIsNoLongerPresent() {
        final Identity identity = IdentityMother.emptyDataBuilder()
                .aliases(AliasesMother.aliases())
                .build();
        dao.save(identity);
        final Identity updateIdentity = IdentityMother.emptyDataBuilder()
                .aliases(Aliases.with(AliasesMother.idvId()))
                .build();
        dao.save(updateIdentity);

        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.idvId());

        assertThat(loadedIdentity).contains(updateIdentity);
    }

}
