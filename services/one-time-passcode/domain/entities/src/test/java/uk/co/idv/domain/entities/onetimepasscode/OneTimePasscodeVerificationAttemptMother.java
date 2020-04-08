package uk.co.idv.domain.entities.onetimepasscode;

import java.time.Instant;

public class OneTimePasscodeVerificationAttemptMother {

    public static OneTimePasscodeVerificationAttempt attempt() {
        return attempt("12345678");
    }

    public static OneTimePasscodeVerificationAttempt attempt(final String passcode) {
        return OneTimePasscodeVerificationAttempt.builder()
                .created(Instant.parse("2019-09-21T20:45:32.233721Z"))
                .passcode(passcode)
                .build();
    }

}
