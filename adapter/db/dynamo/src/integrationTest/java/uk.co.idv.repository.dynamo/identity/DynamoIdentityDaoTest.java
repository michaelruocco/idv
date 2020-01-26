package uk.co.idv.repository.dynamo.identity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoDbLocalContainer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class DynamoIdentityDaoTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    private IdentityDao dao;

    @BeforeEach
    void setUp() {
        final DynamoConfig config = DYNAMO_DB.getConfig();
        dao = config.identityDao();
    }

    @Test
    void shouldLoadByIdvIdAlias() {
        final Identity identity = new Identity(AliasesMother.aliases());
        dao.save(identity);

        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.idvId());

        assertThat(loadedIdentity).contains(identity);
    }

    @Test
    void shouldLoadByCreditCardNumberAlias() {
        final Identity identity = new Identity(AliasesMother.aliases());
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
        final Identity identity = new Identity(AliasesMother.aliases());
        dao.save(identity);
        final Identity updateIdentity = new Identity(Aliases.with(AliasesMother.idvId()));
        dao.save(updateIdentity);

        final Optional<Identity> loadedIdentity = dao.load(AliasesMother.idvId());

        assertThat(loadedIdentity).contains(updateIdentity);
    }

}
