package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.lockout.state.DefaultLoadLockoutStateRequest;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

@Builder
public class DefaultIdentityUpserter implements IdentityUpserter {

    @Builder.Default
    private final TimeProvider timeProvider = new CurrentTimeProvider();

    private final IdentityService identityService;
    private final LockoutService lockoutService;

    @Override
    public Identity upsertIdentity(final CreateContextRequest request) {
        final Identity identity = identityService.upsert(toUpsertIdentityRequest(request));
        lockoutService.validateState(toLockoutStateRequest(request, identity));
        return identity;
    }

    private UpsertIdentityRequest toUpsertIdentityRequest(final CreateContextRequest request) {
        return UpsertIdentityRequest.builder()
                .channel(request.getChannel())
                .providedAlias(request.getProvidedAlias())
                .build();
    }

    private LockoutStateRequest toLockoutStateRequest(final CreateContextRequest request, final Identity identity) {
        return DefaultLoadLockoutStateRequest.builder()
                .channelId(request.getChannelId())
                .activityName(request.getActivityName())
                .alias(request.getProvidedAlias())
                .idvIdValue(identity.getIdvIdValue())
                .timestamp(timeProvider.now())
                .build();
    }

}
