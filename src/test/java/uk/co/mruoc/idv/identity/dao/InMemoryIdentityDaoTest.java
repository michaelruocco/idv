package uk.co.mruoc.idv.identity.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.model.IdvId;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryIdentityDaoTest {

    private final IdentityDao dao = new InMemoryIdentityDao();

    @Test
    void shouldReturnEmptyOptionalIfIdentityNotFound() {
        final Alias alias = new FakeCreditCardNumber();

        assertThat(dao.load(alias)).isEmpty();
    }

    @Test
    void shouldLoadSavedIdentityByAliases() {
        final Alias idvId = new IdvId("fdf72c98-6b7d-4c0a-8c79-8ce8d5fc2198");
        final Alias creditCardNumber = new FakeCreditCardNumber();
        final Identity identity = new Identity(Aliases.with(idvId, creditCardNumber));

        dao.save(identity);

        assertThat(dao.load(idvId)).contains(identity);
        assertThat(dao.load(creditCardNumber)).contains(identity);
    }

}
