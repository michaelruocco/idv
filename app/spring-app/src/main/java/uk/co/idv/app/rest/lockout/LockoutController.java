package uk.co.idv.app.rest.lockout;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.api.lockout.state.LockoutStateDocument;
import uk.co.idv.api.lockout.state.ResetLockoutStateDocument;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.usecases.lockout.LockoutFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lockout-states")
public class LockoutController {

    private final AliasFactory aliasFactory;
    private final LockoutFacade lockoutFacade;

    @GetMapping
    public LockoutStateDocument getLockoutState(@RequestParam final String channelId,
                                                @RequestParam final String activityName,
                                                @RequestParam final String aliasType,
                                                @RequestParam final String aliasValue) {
        final LockoutRequest request = toRequest(channelId, activityName, aliasType, aliasValue);
        final LockoutState state = lockoutFacade.loadLockoutState(request);
        return toDocument(state);
    }

    @PatchMapping
    public LockoutStateDocument resetLockoutState(@RequestBody final ResetLockoutStateDocument document) {
        final LockoutRequest request = document.getAttributes();
        final LockoutState state = lockoutFacade.resetLockoutState(request);
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
