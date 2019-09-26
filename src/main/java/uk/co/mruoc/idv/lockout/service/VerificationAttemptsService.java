package uk.co.mruoc.idv.lockout.service;

import uk.co.mruoc.idv.lockout.domain.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.VerificationAttempts;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.util.UUID;

public interface VerificationAttemptsService {

    VerificationAttempts recordAttempt(final VerificationResult result, final VerificationContext context);

    VerificationAttempts recordAttempt(final VerificationAttempt attempt);

    VerificationAttempts load(final UUID idvId);

}
