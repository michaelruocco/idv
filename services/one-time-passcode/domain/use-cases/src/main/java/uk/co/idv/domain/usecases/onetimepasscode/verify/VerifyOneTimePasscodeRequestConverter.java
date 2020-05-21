package uk.co.idv.domain.usecases.onetimepasscode.verify;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerifyOneTimePasscodeRequestConverter {

    private final TimeProvider timeProvider;

    public Collection<OneTimePasscodeVerificationAttempt> toAttempts(final VerifyOneTimePasscodeRequest request) {
        return request.getPasscodes().stream()
                .map(this::toAttempt)
                .collect(Collectors.toList());
    }

    private OneTimePasscodeVerificationAttempt toAttempt(final String passcode) {
        return OneTimePasscodeVerificationAttempt.builder()
                .passcode(passcode)
                .created(timeProvider.now())
                .build();
    }

}
