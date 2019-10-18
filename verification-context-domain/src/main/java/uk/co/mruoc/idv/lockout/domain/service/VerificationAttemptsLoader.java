package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.UUID;

public interface VerificationAttemptsLoader {

    VerificationAttempts load(UUID idvId);

}
