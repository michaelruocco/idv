package uk.co.idv.domain.usecases.lockout.attempt;

import lombok.Builder;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;

import java.util.UUID;

@Builder
public class DefaultVerificationAttemptsLoader implements VerificationAttemptsLoader {

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    private final VerificationAttemptDao dao;

    @Override
    public VerificationAttempts load(final UUID idvId) {
        return dao.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private VerificationAttempts buildEmptyAttempts(final UUID idvId) {
        return new VerificationAttempts(idGenerator.generate(), idvId);
    }

}
