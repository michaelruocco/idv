package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts.CannotAddAttemptException;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerificationAttemptsTest {


    @Test
    void shouldReturnIdvId() {
        final UUID idvId = UUID.randomUUID();

        final VerificationAttempts attempts = new VerificationAttempts(idvId);

        assertThat(attempts.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldThrowExceptionIfIdvIdOnAttemptDoesNotMatch() {
        final UUID idvId = UUID.randomUUID();
        final VerificationAttempts attempts = new VerificationAttempts(idvId);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final UUID attemptIdvId = UUID.randomUUID();
        when(attempt.getIdvId()).thenReturn(attemptIdvId);

        final Throwable error = catchThrowable(() -> attempts.add(attempt));

        assertThat(error)
                .isInstanceOf(CannotAddAttemptException.class)
                .hasMessage(String.format("attempt idv id %s does not match attempts idv id %s", attemptIdvId, idvId));
    }

    @Test
    void shouldAddAttemptIfIdvIdMatches() {
        final UUID idvId = UUID.randomUUID();
        final VerificationAttempts attempts = new VerificationAttempts(idvId);
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        given(attempt.getIdvId()).willReturn(idvId);

        final VerificationAttempts updatedAttempts = attempts.add(attempt);

        assertThat(updatedAttempts).containsExactly(attempt);
    }

    @Test
    void shouldCopyIdvIdWhenReset() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.reset();

        assertThat(resetAttempts.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldRemoveAllAttemptsWhenReset() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.reset();

        assertThat(resetAttempts).isEmpty();
    }

    @Test
    void shouldCopyIdvIdWhenResetByAlias() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.resetByAlias(new FakeCreditCardNumber());

        assertThat(resetAttempts.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldRemoveAllAttemptsWithAliasWhenResetByAlias() {
        final UUID idvId = UUID.randomUUID();
        final Alias alias = new FakeCreditCardNumber();
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempt attemptWithAlias = mock(VerificationAttempt.class);
        given(attemptWithAlias.getProvidedAlias()).willReturn(alias);
        final VerificationAttempts attempts = new VerificationAttempts(idvId, Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.resetByAlias(alias);

        assertThat(resetAttempts).containsExactly(attempt);
    }

    @Test
    void shouldCopyIdvIdWhenResetByChannelId() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.resetByChannel("channel-id");

        assertThat(resetAttempts.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldRemoveAllAttemptsWithAliasWhenResetByChannelId() {
        final UUID idvId = UUID.randomUUID();
        final String channelId = "channel-id";
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempt attemptWithChannelId = mock(VerificationAttempt.class);
        given(attemptWithChannelId.getChannelId()).willReturn(channelId);
        final VerificationAttempts attempts = new VerificationAttempts(idvId, Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.resetByChannel(channelId);

        assertThat(resetAttempts).containsExactly(attempt);
    }

    @Test
    void shouldCopyIdvIdWhenResetByActivityName() {
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempts attempts = new VerificationAttempts(UUID.randomUUID(), Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.resetByActivity("activity-name");

        assertThat(resetAttempts.getIdvId()).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldRemoveAllAttemptsWithAliasWhenResetByActivityName() {
        final UUID idvId = UUID.randomUUID();
        final String activityName = "activity-name";
        final VerificationAttempt attempt = mock(VerificationAttempt.class);
        final VerificationAttempt attemptWithChannelId = mock(VerificationAttempt.class);
        given(attemptWithChannelId.getActivityName()).willReturn(activityName);
        final VerificationAttempts attempts = new VerificationAttempts(idvId, Collections.singleton(attempt));

        final VerificationAttempts resetAttempts = attempts.resetByActivity(activityName);

        assertThat(resetAttempts).containsExactly(attempt);
    }

    @Test
    void shouldPrintDetails() {
        final UUID idvId = UUID.fromString("cc0da921-c677-4ff7-8e4b-c7f72c8b944e");

        final VerificationAttempts attempts = new VerificationAttempts(idvId);

        assertThat(attempts.toString()).isEqualTo("VerificationAttempts(" +
                "idvId=cc0da921-c677-4ff7-8e4b-c7f72c8b944e, " +
                "attempts=[])");
    }

}
