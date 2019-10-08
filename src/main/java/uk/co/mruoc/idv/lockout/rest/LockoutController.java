package uk.co.mruoc.idv.lockout.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutFacade;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.jsonapi.LockoutStateDocument;
import uk.co.mruoc.idv.lockout.jsonapi.ResetLockoutStateAttributes;
import uk.co.mruoc.idv.lockout.jsonapi.ResetLockoutStateDocument;

@RestController
@RequiredArgsConstructor
public class LockoutController {

    private final AliasFactory aliasFactory;
    private final LockoutFacade lockoutFacade;

    @GetMapping("/lockoutStates")
    public LockoutStateDocument getLockoutState(@RequestParam final String channelId,
                                                @RequestParam final String activityName,
                                                @RequestParam final String aliasType,
                                                @RequestParam final String aliasValue) {
        final LockoutRequest request = toRequest(channelId, activityName, aliasType, aliasValue);
        final LockoutState state = lockoutFacade.getLockoutState(request);
        return toDocument(state);
    }

    @PatchMapping("/lockoutStates")
    public LockoutStateDocument resetLockoutState(@RequestBody final ResetLockoutStateDocument document) {
        final ResetLockoutStateAttributes attributes = document.getAttributes();
        final LockoutState state = lockoutFacade.resetLockoutState(attributes);
        return toDocument(state);
    }

    private LockoutRequest toRequest(final String channelId,
                                     final String activityName,
                                     final String aliasType,
                                     final String aliasValue) {
        final Alias alias = aliasFactory.build(aliasType, aliasValue);
        return DefaultLockoutRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .alias(alias)
                .build();
    }

    private static LockoutStateDocument toDocument(final LockoutState state) {
        return new LockoutStateDocument(state);
    }

}
