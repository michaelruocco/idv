package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.entities.lockout.VerificationAttempts;

import java.util.UUID;

@Builder
public class DefaultVerificationAttemptsLoader implements VerificationAttemptsLoader {

    private final IdGenerator idGenerator;
    private final VerificationAttemptsDao dao;

    @Override
    public VerificationAttempts load(final UUID idvId) {
        return dao.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private VerificationAttempts buildEmptyAttempts(final UUID idvId) {
        return new VerificationAttempts(idGenerator.generate(), idvId);
    }

}
