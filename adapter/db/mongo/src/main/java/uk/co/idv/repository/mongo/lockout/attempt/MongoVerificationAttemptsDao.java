package uk.co.idv.repository.mongo.lockout.attempt;

import lombok.Builder;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

@Builder
public class MongoVerificationAttemptsDao implements VerificationAttemptsDao {

    private final VerificationAttemptsRepository repository;
    private final VerificationAttemptsDocumentConverter converter;

    @Override
    public void save(final VerificationAttempts attempts) {
        final VerificationAttemptsDocument document = converter.toAttemptsDocument(attempts);
        repository.save(document);
    }

    @Override
    public Optional<VerificationAttempts> load(final UUID id) {
        final Optional<VerificationAttemptsDocument> document = repository.findById(id.toString());
        return document.map(converter::toAttempts);
    }

}
