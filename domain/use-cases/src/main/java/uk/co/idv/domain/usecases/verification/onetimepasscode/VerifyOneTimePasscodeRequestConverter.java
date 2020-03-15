package uk.co.idv.domain.usecases.verification.onetimepasscode;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.util.TimeGenerator;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VerifyOneTimePasscodeRequestConverter {

    private final TimeGenerator timeGenerator;

    public Collection<OneTimePasscodeVerificationAttempt> toAttempts(final VerifyOneTimePasscodeRequest request) {
        return request.getPasscodes().stream()
                .map(this::toAttempt)
                .collect(Collectors.toList());
    }

    private OneTimePasscodeVerificationAttempt toAttempt(final String passcode) {
        return OneTimePasscodeVerificationAttempt.builder()
                .passcode(passcode)
                .created(timeGenerator.now())
                .build();
    }

}
