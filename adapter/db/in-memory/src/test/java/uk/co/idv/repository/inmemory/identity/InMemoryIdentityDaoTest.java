package uk.co.idv.repository.inmemory.identity;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;
import uk.co.mruoc.idv.identity.domain.model.Identity;

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
        final Identity identity = new Identity(Aliases.with(idvId, creditCardNumber));

        dao.save(identity);

        assertThat(dao.load(idvId)).contains(identity);
        assertThat(dao.load(creditCardNumber)).contains(identity);
    }

}
