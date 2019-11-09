package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.identity.LoadIdentityRequest;
import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.idv.domain.usecases.util.TimeGenerator;

@Builder
public class DefaultLockoutFacade implements LockoutFacade {

    private final TimeGenerator timeGenerator;
    private final IdentityService identityService;
    private final LockoutService lockoutService;

    @Override
    public LockoutState loadLockoutState(final LockoutRequest request) {
        final LockoutStateRequest stateRequest = toLockoutStateRequest(request);
        return lockoutService.loadState(stateRequest);
    }

    @Override
    public LockoutState resetLockoutState(final LockoutRequest request) {
        final LockoutStateRequest stateRequest = toLockoutStateRequest(request);
        return lockoutService.resetState(stateRequest);
    }

    private LockoutStateRequest toLockoutStateRequest(final LockoutRequest request) {
        final Identity identity = loadIdentity(request.getAlias());
        return toLockoutStateRequest(request, identity);
    }

    private Identity loadIdentity(final Alias alias) {
        final LoadIdentityRequest request = toLoadIdentityRequest(alias);
        return identityService.load(request);
    }

    private LoadIdentityRequest toLoadIdentityRequest(final Alias alias) {
        return LoadIdentityRequest.builder()
                .alias(alias)
                .build();
    }

    private LockoutStateRequest toLockoutStateRequest(final LockoutRequest request,
                                                      final Identity identity) {
        return DefaultLoadLockoutStateRequest.builder()
                .timestamp(timeGenerator.now())
                .channelId(request.getChannelId())
                .activityName(request.getActivityName())
                .alias(request.getAlias())
                .idvIdValue(identity.getIdvIdValue())
                .build();
    }

}
