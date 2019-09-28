package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.UUID;

@Builder
public class VerificationAttemptsLoader {

    private final VerificationAttemptsDao dao;

    public VerificationAttempts load(final UUID idvId) {
        return dao.load(idvId).orElse(buildEmptyAttempts(idvId));
    }

    private static VerificationAttempts buildEmptyAttempts(final UUID idvId) {
        return new VerificationAttempts(idvId);
    }

}
