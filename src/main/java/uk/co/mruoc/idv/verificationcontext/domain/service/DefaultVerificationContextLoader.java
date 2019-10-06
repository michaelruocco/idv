package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLoadLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.time.Instant;
import java.util.UUID;

@Builder
public class DefaultVerificationContextLoader implements VerificationContextLoader {

    private final VerificationContextDao dao;
    private final LockoutService lockoutService;
    private final TimeService timeService;

    @Override
    public VerificationContext load(final LoadContextRequest request) {
        return load(request.getId());
    }

    private VerificationContext load(final UUID id) {
        final VerificationContext context = dao.load(id).orElseThrow(() -> new VerificationContextNotFoundException(id));
        validateExpiry(context);
        validateLockoutState(context);
        return context;
    }

    private void validateExpiry(final VerificationContext context) {
        final Instant now = timeService.now();
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
                .timestamp(timeService.now())
                .build();
        lockoutService.validateState(request);
    }

}
