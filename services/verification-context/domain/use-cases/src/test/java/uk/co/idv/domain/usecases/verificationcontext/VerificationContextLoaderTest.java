package uk.co.idv.domain.usecases.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.usecases.util.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.TimeProvider;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.usecases.lockout.FakeLockoutService;
import uk.co.idv.domain.usecases.lockout.LockoutStateValidator.LockedOutException;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader.VerificationContextExpiredException;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader.VerificationContextNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextLoaderTest {

    private static final Instant NOW = Instant.parse("2019-09-21T20:46:32.233721Z");

    private final TimeProvider timeProvider = new FakeTimeProvider(NOW);
    private final FakeLockoutService lockoutService = new FakeLockoutService();
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextLoader loader = DefaultVerificationContextLoader.builder()
            .dao(dao)
            .lockoutService(lockoutService)
            .timeProvider(timeProvider)
            .build();

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        final UUID id = UUID.randomUUID();
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> loader.load(id));

        assertThat(error)
                .isInstanceOf(VerificationContextNotFoundException.class)
                .hasMessage(String.format("verification context with id %s not found", id.toString()));
    }

    @Test
    void shouldThrowExceptionIfContextHasExpired() {
        final UUID id = UUID.fromString("7d753825-4fc7-4eec-b6fe-43073884b778");
        final VerificationContext context = mock(VerificationContext.class);
        given(context.getId()).willReturn(id);
        given(context.hasExpired(NOW)).willReturn(true);
        given(context.getExpiry()).willReturn(NOW.minusSeconds(30));
        given(dao.load(id)).willReturn(Optional.of(context));

        final Throwable error = catchThrowable(() -> loader.load(id));

        assertThat(error)
                .isInstanceOf(VerificationContextExpiredException.class)
                .hasMessage(String.format("context %s expired at %s current time is %s", id, context.getExpiry(), NOW));
    }

    @Test
    void shouldPassChannelWhenValidatingLockoutState() {
        final VerificationContext context = VerificationContextMother.fake();
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        loader.load(context.getId());

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getChannelId()).isEqualTo(context.getChannelId());
    }

    @Test
    void shouldPassActivityWhenValidatingLockoutState() {
        final VerificationContext context = VerificationContextMother.fake();
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        loader.load(context.getId());

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldPassProvidedAliasWhenValidatingLockoutState() {
        final VerificationContext context = VerificationContextMother.fake();
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        loader.load(context.getId());

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldPassProvidedIdvIdValueWhenValidatingLockoutState() {
        final VerificationContext context = VerificationContextMother.fake();
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        loader.load(context.getId());

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimeWhenValidatingLockoutState() {
        final VerificationContext context = VerificationContextMother.fake();
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        loader.load(context.getId());

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getTimestamp()).isEqualTo(NOW);
    }

    @Test
    void shouldThrowExceptionIfLockoutStateIsLocked() {
        final LockoutState lockedState = mock(LockoutState.class);
        lockoutService.setHasLockedState(lockedState);
        final VerificationContext context = mock(VerificationContext.class);
        given(context.hasExpired(NOW)).willReturn(false);
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        final LockedOutException error = (LockedOutException) catchThrowable(() -> loader.load(context.getId()));

        assertThat(error).hasMessage("locked");
        assertThat(error.getLockoutState()).isEqualTo(lockedState);
    }

    @Test
    void shouldLoadContext() {
        final VerificationContext context = VerificationContextMother.fake();
        given(dao.load(context.getId())).willReturn(Optional.of(context));

        final VerificationContext loadedContext = loader.load(context.getId());

        assertThat(loadedContext).isEqualTo(context);
    }

}
