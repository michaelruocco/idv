package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

@Builder
public class MongoVerificationAttemptsDao implements VerificationAttemptsDao {

    private final VerificationAttemptsRepository repository;
    private final VerificationAttemptsConverter converter;

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
