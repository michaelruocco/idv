package uk.co.mruoc.idv.lockout.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.identity.domain.service.LoadIdentityRequest;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLoadLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LoadLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;

@RestController
@RequiredArgsConstructor
public class LockoutController {

    private final TimeService timeService;
    private final LockoutService service;
    private final AliasFactory aliasFactory;
    private final IdentityService identityService;

    @GetMapping("/lockoutStates")
    public LockoutState getLockoutState(@RequestParam final String channelId,
                                        @RequestParam final String activityName,
                                        @RequestParam final String aliasType,
                                        @RequestParam final String aliasValue) {
        final Alias alias = aliasFactory.build(aliasType, aliasValue);
        final Identity identity = loadIdentity(alias);
        return loadLockoutState(channelId, activityName, alias, identity);
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

    private LockoutState loadLockoutState(final String channelId,
                                          final String activityName,
                                          final Alias alias,
                                          final Identity identity) {
        final LoadLockoutStateRequest request = toLoadLockoutStateRequest(channelId, activityName, alias, identity);
        return service.loadState(request);
    }

    private LoadLockoutStateRequest toLoadLockoutStateRequest(final String channelId,
                                                              final String activityName,
                                                              final Alias alias,
                                                              final Identity identity) {
        return DefaultLoadLockoutStateRequest.builder()
                .timestamp(timeService.now())
                .channelId(channelId)
                .activityName(activityName)
                .alias(alias)
                .idvIdValue(identity.getIdvIdValue())
                .build();
    }

}
