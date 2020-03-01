package uk.co.idv.domain.entities.verification.onetimepasscode;

import java.time.Instant;

public class OneTimePasscodeVerificationAttemptMother {

    public static OneTimePasscodeVerificationAttempt attempt() {
        return attempt("12345678");
    }

    public static OneTimePasscodeVerificationAttempt attempt(final String passcode) {
        return OneTimePasscodeVerificationAttempt.builder()
                .created(Instant.now())
                .passcode(passcode)
                .build();
    }

}
