package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationAttemptsLoaderTest {

    private static final UUID ATTEMPTS_ID = UUID.randomUUID();
    private static final UUID IDV_ID_VALUE = UUID.randomUUID();

    private final IdGenerator idGenerator = new FakeIdGenerator(ATTEMPTS_ID);
    private final VerificationAttemptsDao dao = mock(VerificationAttemptsDao.class);

    private final VerificationAttemptsLoader loader = DefaultVerificationAttemptsLoader.builder()
            .idGenerator(idGenerator)
            .dao(dao)
            .build();

    @Test
    void shouldReturnEmptyAttemptsIfAttemptsNotFound() {
        given(dao.load(IDV_ID_VALUE)).willReturn(Optional.empty());

        final VerificationAttempts attempts = loader.load(IDV_ID_VALUE);

        assertThat(attempts).isEmpty();
    }

    @Test
    void shouldPopulateIdvIdOnAttemptsIfAttemptsNotFound() {
        given(dao.load(IDV_ID_VALUE)).willReturn(Optional.empty());

        final VerificationAttempts attempts = loader.load(IDV_ID_VALUE);

        assertThat(attempts.getIdvId()).isEqualTo(IDV_ID_VALUE);
    }

    @Test
    void shouldPopulateRandomIdOnAttemptsIfAttemptsNotFound() {
        given(dao.load(IDV_ID_VALUE)).willReturn(Optional.empty());

        final VerificationAttempts attempts = loader.load(IDV_ID_VALUE);

        assertThat(attempts.getId()).isEqualTo(ATTEMPTS_ID);
    }

    @Test
    void shouldReturnLoadedAttempts() {
        final VerificationAttempts expectedAttempts = new FakeVerificationAttempts();
        given(dao.load(IDV_ID_VALUE)).willReturn(Optional.of(expectedAttempts));

        final VerificationAttempts attempts = loader.load(IDV_ID_VALUE);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

}
