package uk.co.idv.domain.usecases.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;

import java.time.Instant;

@Builder
public class OneTimePasscodeVerificationFactory {

    private final IdGenerator idGenerator;
    private final TimeGenerator timeGenerator;

    public OneTimePasscodeVerification build(final VerificationContext context) {
        final OneTimePasscodeEligible method = context.getNextOneTimePasscodeEligibleMethod();
        final Instant created = timeGenerator.now();
        final PasscodeSettings settings = method.getPasscodeSettings();
        return OneTimePasscodeVerification.builder()
                .id(idGenerator.generate())
                .contextId(context.getId())
                .created(created)
                .expiry(created.plus(method.getDuration()))
                .maxAttempts(method.getMaxAttempts())
                .maxDeliveryAttempts(settings.getMaxDeliveryAttempts())
                .build();
    }

}
