package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResult;

import java.time.Instant;
import java.util.UUID;

public class OneTimePasscodeVerificationResult extends DefaultVerificationResult {

    public OneTimePasscodeVerificationResult(final UUID verificationId, final Instant timestamp, boolean successful) {
        super(OneTimePasscode.NAME, verificationId, timestamp, successful);
    }

}
