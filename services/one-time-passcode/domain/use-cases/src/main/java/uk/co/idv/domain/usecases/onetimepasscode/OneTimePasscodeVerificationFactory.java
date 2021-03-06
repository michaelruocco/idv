package uk.co.idv.domain.usecases.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;

@Builder
public class OneTimePasscodeVerificationFactory {

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    @Builder.Default
    private final TimeProvider timeProvider = new CurrentTimeProvider();

    public OneTimePasscodeVerification build(final VerificationContext context) {
        final OneTimePasscode method = context.getNextOneTimePasscodeEligibleMethod();
        final Instant created = timeProvider.now();
        return OneTimePasscodeVerification.builder()
                .id(idGenerator.generate())
                .contextId(context.getId())
                .created(created)
                .expiry(created.plus(method.getDuration()))
                .maxAttempts(method.getMaxAttempts())
                .maxDeliveries(method.getMaxDeliveries())
                .build();
    }

}
