package uk.co.idv.repository.mongo.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.model.FakeVerificationAttempts;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoVerificationAttemptsDaoTest {

    private final VerificationAttempts attempts = new FakeVerificationAttempts();
    private final VerificationAttemptsDocument document = mock(VerificationAttemptsDocument.class);

    private final VerificationAttemptsRepository repository = mock(VerificationAttemptsRepository.class);
    private final VerificationAttemptsDocumentConverter converter = mock(VerificationAttemptsDocumentConverter.class);

    private final MongoVerificationAttemptsDao dao = MongoVerificationAttemptsDao.builder()
            .repository(repository)
            .converter(converter)
            .build();

    @Test
    void shouldSaveConvertedAttempts() {
        given(converter.toAttemptsDocument(attempts)).willReturn(document);

        dao.save(attempts);

        verify(repository).save(document);
    }

    @Test
    void shouldLoadAttempts() {
        given(repository.findById(attempts.getIdvId().toString())).willReturn(Optional.of(document));
        given(converter.toAttempts(document)).willReturn(attempts);

        final Optional<VerificationAttempts> loadedAttempts = dao.load(attempts.getIdvId());

        assertThat(loadedAttempts).contains(attempts);
    }

    @Test
    void shouldLoadEmptyOptionalIfNotFound() {
        given(repository.findById(attempts.getIdvId().toString())).willReturn(Optional.empty());

        final Optional<VerificationAttempts> loadedAttempts = dao.load(attempts.getIdvId());

        assertThat(loadedAttempts).isEmpty();
    }

}
