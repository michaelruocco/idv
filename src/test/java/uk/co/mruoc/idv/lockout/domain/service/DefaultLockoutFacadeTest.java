package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.service.FakeTimeService;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdentity;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.FakeIdentityService;
import uk.co.mruoc.idv.identity.domain.service.LoadIdentityRequest;
import uk.co.mruoc.idv.lockout.domain.model.FakeLockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutFacadeTest {

    private final Instant now = Instant.now();
    private final Identity identity = new FakeIdentity();

    private final TimeService timeService = new FakeTimeService(now);
    private final FakeIdentityService identityService = new FakeIdentityService(identity);
    private final FakeLockoutService lockoutService = new FakeLockoutService();

    private final LockoutFacade facade = DefaultLockoutFacade.builder()
            .timeService(timeService)
            .identityService(identityService)
            .lockoutService(lockoutService)
            .build();

    @Test
    void shouldLoadIdentityUsingAliasBeforeResettingLockoutState() {
        final Alias alias = new FakeCreditCardNumber();
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .alias(alias)
                .build();

        facade.resetLockoutState(request);

        final LoadIdentityRequest lastLoadIdentityRequest = identityService.getLastLoadRequest();
        assertThat(lastLoadIdentityRequest.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldPassChannelIdWhenResettingLockoutState() {
        final String channelId = "channel-id";
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(channelId)
                .build();

        facade.resetLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastResetStateRequest();
        assertThat(lastResetRequest.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPassActivityNameWhenResettingLockoutState() {
        final String activityName = "activity-name";
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .activityName(activityName)
                .build();

        facade.resetLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastResetStateRequest();
        assertThat(lastResetRequest.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPassAliasWhenResettingLockoutState() {
        final Alias alias = new FakeCreditCardNumber();
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .alias(alias)
                .build();

        facade.resetLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastResetStateRequest();
        assertThat(lastResetRequest.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldPassIdvIdValueFromLoadedIdentityWhenResettingLockoutState() {
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        facade.resetLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastResetStateRequest();
        assertThat(lastResetRequest.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimeWhenResettingLockoutState() {
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        facade.resetLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastResetStateRequest();
        assertThat(lastResetRequest.getTimestamp()).isEqualTo(now);
    }

    @Test
    void shouldReturnLockoutStateWhenResettingLockoutState() {
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        lockoutService.setLockoutStateToReturn(expectedState);
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        final LockoutState state = facade.resetLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldLoadIdentityUsingAliasBeforeLoadingLockoutState() {
        final Alias alias = new FakeCreditCardNumber();
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .alias(alias)
                .build();

        facade.loadLockoutState(request);

        final LoadIdentityRequest lastLoadIdentityRequest = identityService.getLastLoadRequest();
        assertThat(lastLoadIdentityRequest.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldPassChannelIdWhenLoadingLockoutState() {
        final String channelId = "channel-id";
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(channelId)
                .build();

        facade.loadLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastLoadStateRequest();
        assertThat(lastResetRequest.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldPassActivityNameWhenLoadingLockoutState() {
        final String activityName = "activity-name";
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .activityName(activityName)
                .build();

        facade.loadLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastLoadStateRequest();
        assertThat(lastResetRequest.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldPassAliasWhenLoadingLockoutState() {
        final Alias alias = new FakeCreditCardNumber();
        final LockoutRequest request = DefaultLockoutRequest.builder()
                .alias(alias)
                .build();

        facade.loadLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastLoadStateRequest();
        assertThat(lastResetRequest.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldPassIdvIdValueFromLoadedIdentityWhenLoadingLockoutState() {
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        facade.loadLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastLoadStateRequest();
        assertThat(lastResetRequest.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimeWhenLoadingLockoutState() {
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        facade.loadLockoutState(request);

        final LockoutStateRequest lastResetRequest = lockoutService.getLastLoadStateRequest();
        assertThat(lastResetRequest.getTimestamp()).isEqualTo(now);
    }

    @Test
    void shouldReturnLockoutStateWhenLoadingLockoutState() {
        final LockoutState expectedState = new FakeLockoutStateMaxAttempts();
        lockoutService.setLockoutStateToReturn(expectedState);
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        final LockoutState state = facade.loadLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
    }

}
