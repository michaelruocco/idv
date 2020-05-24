package uk.co.idv.domain.usecases.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.identity.FakeIdentityService;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.lockout.FakeLockoutService;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateValidator.LockedOutException;
import uk.co.idv.domain.usecases.util.time.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class DefaultIdentityUpserterTest {

    private static final Instant NOW = Instant.now();
    private final TimeProvider timeProvider = new FakeTimeProvider(NOW);

    private final Identity identity = IdentityMother.build();
    private final FakeIdentityService identityService = new FakeIdentityService(identity);

    private final FakeLockoutService lockoutService = new FakeLockoutService();

    private final IdentityUpserter upserter = DefaultIdentityUpserter.builder()
            .timeProvider(timeProvider)
            .identityService(identityService)
            .lockoutService(lockoutService)
            .build();

    @Test
    void shouldPassChannelWhenUpsertingIdentity() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final UpsertIdentityRequest identityRequest = identityService.getLastUpsertRequest();
        assertThat(identityRequest.getChannel()).isEqualTo(createContextRequest.getChannel());
    }

    @Test
    void shouldPassProvidedAliasWhenUpsertingIdentity() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final UpsertIdentityRequest identityRequest = identityService.getLastUpsertRequest();
        assertThat(identityRequest.getProvidedAlias()).isEqualTo(createContextRequest.getProvidedAlias());
    }

    @Test
    void shouldPassChannelWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getChannelId()).isEqualTo(createContextRequest.getChannelId());
    }

    @Test
    void shouldPassActivityWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getActivityName()).isEqualTo(createContextRequest.getActivityName());
    }

    @Test
    void shouldPassProvidedAliasWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getAlias()).isEqualTo(createContextRequest.getProvidedAlias());
    }

    @Test
    void shouldPassIdvIdValueWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimestampWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        upserter.upsertIdentity(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getTimestamp()).isEqualTo(NOW);
    }

    @Test
    void shouldThrowExceptionIfLockoutStateIsLocked() {
        final LockoutState lockedState = mock(LockoutState.class);
        lockoutService.setHasLockedState(lockedState);
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        final LockedOutException error = (LockedOutException) catchThrowable(() -> upserter.upsertIdentity(createContextRequest));

        assertThat(error).hasMessage("locked");
        assertThat(error.getLockoutState()).isEqualTo(lockedState);
    }

}
