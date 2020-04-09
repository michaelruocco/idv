package uk.co.idv.repository.inmemory.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryOneTimePasscodeVerificationDaoTest {

    private final OneTimePasscodeVerificationDao dao = new InMemoryOneTimePasscodeVerificationDao();

    @Test
    void shouldReturnEmptyOptionalIfVerificationNotFound() {
        final UUID id = UUID.randomUUID();

        final Optional<OneTimePasscodeVerification> loadedVerification = dao.load(id);

        assertThat(loadedVerification).isEmpty();
    }

    @Test
    void shouldLoadSavedVerification() {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();
        dao.save(verification);

        final Optional<OneTimePasscodeVerification> loadedVerification = dao.load(verification.getId());

        assertThat(loadedVerification).contains(verification);
    }

}
