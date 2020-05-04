package uk.co.idv.domain.entities.onetimepasscode;

import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt.OneTimePasscodeVerificationAttemptBuilder;

import java.time.Instant;

public class OneTimePasscodeVerificationAttemptMother {

    public static OneTimePasscodeVerificationAttempt attempt() {
        return builder().build();
    }

    public static OneTimePasscodeVerificationAttempt attempt(final String passcode) {
        return OneTimePasscodeVerificationAttempt.builder()
                .created(Instant.parse("2019-09-21T20:45:32.233721Z"))
                .passcode(passcode)
                .build();
    }

    public static OneTimePasscodeVerificationAttemptBuilder builder() {
        return OneTimePasscodeVerificationAttempt.builder()
                .created(Instant.parse("2019-09-21T20:45:32.233721Z"))
                .passcode("12345678");
    }

}
