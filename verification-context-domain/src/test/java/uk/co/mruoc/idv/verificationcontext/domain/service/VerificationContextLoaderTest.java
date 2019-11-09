package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.util.FakeTimeGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.FakeLockoutService;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator.LockedOutException;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextExpiredException;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader.VerificationContextNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VerificationContextLoaderTest {

    private static final Instant NOW = Instant.parse("2019-09-21T20:46:32.233721Z");

    private final TimeGenerator timeGenerator = new FakeTimeGenerator(NOW);
    private final FakeLockoutService lockoutService = new FakeLockoutService();
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextLoader loader = DefaultVerificationContextLoader.builder()
            .dao(dao)
            .lockoutService(lockoutService)
            .timeGenerator(timeGenerator)
            .build();

    @Test
    void shouldThrowExceptionIfContextNotFound() {
        final UUID id = UUID.randomUUID();
        final LoadContextRequest request = toLoadContextRequest(id);
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> loader.load(request));

        assertThat(error)
                .isInstanceOf(VerificationContextNotFoundException.class)
                .hasMessage(String.format("verification context with id %s not found", id.toString()));
    }

    @Test
    void shouldThrowExceptionIfContextHasExpired() {
        final UUID id = UUID.fromString("7d753825-4fc7-4eec-b6fe-43073884b778");
        final LoadContextRequest request = toLoadContextRequest(id);
        final VerificationContext context = mock(VerificationContext.class);
        given(context.getId()).willReturn(id);
        given(context.hasExpired(NOW)).willReturn(true);
        given(context.getExpiry()).willReturn(NOW.minusSeconds(30));
        given(dao.load(id)).willReturn(Optional.of(context));

        final Throwable error = catchThrowable(() -> loader.load(request));

        assertThat(error)
                .isInstanceOf(VerificationContextExpiredException.class)
                .hasMessage(String.format("context %s expired at %s current time is %s", id, context.getExpiry(), NOW));
    }

    @Test
    void shouldPassChannelWhenValidatingLockoutState() {
        final VerificationContext context = new FakeVerificationContext();
        given(dao.load(context.getId())).willReturn(Optional.of(context));
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        loader.load(request);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getChannelId()).isEqualTo(context.getChannelId());
    }

    @Test
    void shouldPassActivityWhenValidatingLockoutState() {
        final VerificationContext context = new FakeVerificationContext();
        given(dao.load(context.getId())).willReturn(Optional.of(context));
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        loader.load(request);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getActivityName()).isEqualTo(context.getActivityName());
    }

    @Test
    void shouldPassProvidedAliasWhenValidatingLockoutState() {
        final VerificationContext context = new FakeVerificationContext();
        given(dao.load(context.getId())).willReturn(Optional.of(context));
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        loader.load(request);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getAlias()).isEqualTo(context.getProvidedAlias());
    }

    @Test
    void shouldPassProvidedIdvIdValueWhenValidatingLockoutState() {
        final VerificationContext context = new FakeVerificationContext();
        given(dao.load(context.getId())).willReturn(Optional.of(context));
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        loader.load(request);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getIdvIdValue()).isEqualTo(context.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimeWhenValidatingLockoutState() {
        final VerificationContext context = new FakeVerificationContext();
        given(dao.load(context.getId())).willReturn(Optional.of(context));
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        loader.load(request);

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
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        final LockedOutException error = (LockedOutException) catchThrowable(() -> loader.load(request));

        assertThat(error).hasMessage("locked");
        assertThat(error.getLockoutState()).isEqualTo(lockedState);
    }

    @Test
    void shouldLoadContext() {
        final VerificationContext context = new FakeVerificationContext();
        given(dao.load(context.getId())).willReturn(Optional.of(context));
        final LoadContextRequest request = toLoadContextRequest(context.getId());

        final VerificationContext loadedContext = loader.load(request);

        assertThat(loadedContext).isEqualTo(context);
    }

    private static LoadContextRequest toLoadContextRequest(final UUID id) {
        return LoadContextRequest.builder()
                .id(id)
                .build();
    }

}
