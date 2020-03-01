package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class OneTimePasscodeVerificationAttempt {

    private final Instant created;
    private final String passcode;

}
