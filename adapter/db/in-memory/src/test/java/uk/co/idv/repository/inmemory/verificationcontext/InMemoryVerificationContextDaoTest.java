package uk.co.idv.repository.inmemory.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryVerificationContextDaoTest {

    private final VerificationContextDao dao = new InMemoryVerificationContextDao();

    @Test
    void shouldReturnEmptyOptionalIfContextNotFound() {
        final UUID id = UUID.randomUUID();

        assertThat(dao.load(id)).isEmpty();
    }

    @Test
    void shouldLoadSavedContext() {
        final VerificationContext context = VerificationContext.builder()
                .id(UUID.randomUUID())
                .build();
        dao.save(context);

        final Optional<VerificationContext> loadedContext = dao.load(context.getId());

        assertThat(loadedContext).contains(context);
    }

}
