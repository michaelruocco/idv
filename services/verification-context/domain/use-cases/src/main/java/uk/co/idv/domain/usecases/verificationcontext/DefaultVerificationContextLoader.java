package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.usecases.lockout.state.DefaultLoadLockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.time.Instant;
import java.util.UUID;

@Builder
public class DefaultVerificationContextLoader implements VerificationContextLoader {

    @Builder.Default
    private final TimeProvider timeProvider = new CurrentTimeProvider();

    private final VerificationContextDao dao;
    private final LockoutService lockoutService;

    @Override
    public VerificationContext load(final UUID id) {
        final VerificationContext context = dao.load(id).orElseThrow(() -> new VerificationContextNotFoundException(id));
        validateExpiry(context);
        validateLockoutState(context);
        return context;
    }

    private void validateExpiry(final VerificationContext context) {
        final Instant now = timeProvider.now();
        if (context.hasExpired(now)) {
            throw new VerificationContextExpiredException(context, now);
        }
    }

    private void validateLockoutState(final VerificationContext context) {
        final LockoutStateRequest request = DefaultLoadLockoutStateRequest.builder()
                .channelId(context.getChannelId())
                .activityName(context.getActivityName())
                .alias(context.getProvidedAlias())
                .idvIdValue(context.getIdvIdValue())
                .timestamp(timeProvider.now())
                .build();
        lockoutService.validateState(request);
    }

}
