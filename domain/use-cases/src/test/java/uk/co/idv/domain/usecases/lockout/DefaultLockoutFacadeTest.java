package uk.co.idv.domain.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.util.FakeTimeGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.usecases.identity.FakeIdentityService;
import uk.co.idv.domain.usecases.identity.LoadIdentityRequest;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.FakeMaxAttemptsLockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutFacadeTest {

    private final Instant now = Instant.now();
    private final Identity identity = new Identity(AliasesMother.aliases());

    private final TimeGenerator timeGenerator = new FakeTimeGenerator(now);
    private final FakeIdentityService identityService = new FakeIdentityService(identity);
    private final FakeLockoutService lockoutService = new FakeLockoutService();

    private final LockoutFacade facade = DefaultLockoutFacade.builder()
            .timeGenerator(timeGenerator)
            .identityService(identityService)
            .lockoutService(lockoutService)
            .build();

    @Test
    void shouldLoadIdentityUsingAliasBeforeResettingLockoutState() {
        final Alias alias = AliasesMother.creditCardNumber();
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
        final Alias alias = AliasesMother.creditCardNumber();
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
        final LockoutState expectedState = new FakeMaxAttemptsLockoutState();
        lockoutService.setLockoutStateToReturn(expectedState);
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        final LockoutState state = facade.resetLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
    }

    @Test
    void shouldLoadIdentityUsingAliasBeforeLoadingLockoutState() {
        final Alias alias = AliasesMother.creditCardNumber();
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
        final Alias alias = AliasesMother.creditCardNumber();
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
        final LockoutState expectedState = new FakeMaxAttemptsLockoutState();
        lockoutService.setLockoutStateToReturn(expectedState);
        final LockoutRequest request = DefaultLockoutRequest.builder().build();

        final LockoutState state = facade.loadLockoutState(request);

        assertThat(state).isEqualTo(expectedState);
    }

}
