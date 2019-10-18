package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

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
