package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.identity.domain.service.LoadIdentityRequest;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

@Builder
public class DefaultLockoutFacade implements LockoutFacade {

    private final TimeService timeService;
    private final IdentityService identityService;
    private final LockoutService lockoutService;

    @Override
    public LockoutState getLockoutState(final LockoutRequest request) {
        final Identity identity = loadIdentity(request.getAlias());
        final LockoutStateRequest stateRequest = toLockoutStateRequest(request, identity);
        return lockoutService.loadState(stateRequest);
    }

    @Override
    public LockoutState resetLockoutState(final LockoutRequest request) {
        final Identity identity = loadIdentity(request.getAlias());
        final LockoutStateRequest stateRequest = toLockoutStateRequest(request, identity);
        return lockoutService.resetState(stateRequest);
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
                .timestamp(timeService.now())
                .channelId(request.getChannelId())
                .activityName(request.getActivityName())
                .alias(request.getAlias())
                .idvIdValue(identity.getIdvIdValue())
                .build();
    }

}
