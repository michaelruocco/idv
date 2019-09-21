package uk.co.mruoc.idv.verificationcontext.dao;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao.*;

class InMemoryVerificationContextDaoTest {

    private final VerificationContextDao dao = new InMemoryVerificationContextDao();

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        final UUID id = UUID.randomUUID();

        final Throwable error = catchThrowable(() -> dao.load(id));

        assertThat(error)
                .isInstanceOf(VerificationContextNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldLoadSavedContext() {
        final VerificationContext context = VerificationContext.builder()
                .id(UUID.randomUUID())
                .build();
        dao.save(context);

        final VerificationContext loadedContext = dao.load(context.getId());

        assertThat(loadedContext).isEqualTo(context);
    }

}
