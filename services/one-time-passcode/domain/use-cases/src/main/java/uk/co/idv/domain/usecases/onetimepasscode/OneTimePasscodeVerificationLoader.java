package uk.co.idv.domain.usecases.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;
import java.util.UUID;

@Builder
public class OneTimePasscodeVerificationLoader {

    private final OneTimePasscodeVerificationDao dao;
    private final TimeProvider timeProvider;

    public OneTimePasscodeVerification load(final UUID id) {
        final OneTimePasscodeVerification verification = dao.load(id)
                .orElseThrow(() -> new OneTimePasscodeVerificationNotFoundException(id));
        validateExpiry(verification);
        return verification;
    }

    private void validateExpiry(final OneTimePasscodeVerification verification) {
        final Instant now = timeProvider.now();
        if (verification.hasExpired(now)) {
            throw new OneTimePasscodeVerificationExpiredException(verification, now);
        }
    }

    public static class OneTimePasscodeVerificationNotFoundException extends RuntimeException {

        public OneTimePasscodeVerificationNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

    public static class OneTimePasscodeVerificationExpiredException extends RuntimeException {

        public OneTimePasscodeVerificationExpiredException(final OneTimePasscodeVerification verification, final Instant now) {
            super(String.format("one time passcode verification %s expired at %s current time is %s",
                    verification.getId(),
                    verification.getExpiry(),
                    now));
        }

    }

}
