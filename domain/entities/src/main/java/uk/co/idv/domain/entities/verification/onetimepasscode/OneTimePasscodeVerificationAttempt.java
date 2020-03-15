package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Builder
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class OneTimePasscodeVerificationAttempt {

    private final Instant created;
    private final String passcode;

}
