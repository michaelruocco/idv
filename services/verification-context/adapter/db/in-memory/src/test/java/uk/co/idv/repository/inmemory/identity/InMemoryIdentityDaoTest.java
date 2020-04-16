package uk.co.idv.repository.inmemory.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryIdentityDaoTest {

    private final IdentityDao dao = new InMemoryIdentityDao();

    @Test
    void shouldReturnEmptyOptionalIfIdentityNotFound() {
        final Alias alias = AliasesMother.creditCardNumber();

        assertThat(dao.load(alias)).isEmpty();
    }

    @Test
    void shouldLoadSavedIdentityByAliases() {
        final Alias idvId = AliasesMother.idvId();
        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        final Identity identity = IdentityMother.withAliases(Aliases.with(idvId, creditCardNumber));

        dao.save(identity);

        assertThat(dao.load(idvId)).contains(identity);
        assertThat(dao.load(creditCardNumber)).contains(identity);
    }

}
