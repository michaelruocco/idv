package uk.co.idv.domain.entities.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.ChannelMother;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext.VerificationContextBuilder;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerificationContextTest {

    private final VerificationContextBuilder builder = VerificationContext.builder();

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final VerificationContext context = builder.id(id).build();

        assertThat(context.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnChannel() {
        final Channel channel = ChannelMother.fake();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnHasChannelIdTrueIfChannelIdMatches() {
        final Channel channel = ChannelMother.fake();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.hasChannelId("fake-channel")).isTrue();
    }

    @Test
    void shouldReturnHasChannelIdFalseIfChannelIdDoesNotMatch() {
        final Channel channel = ChannelMother.fake();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.hasChannelId("other-channel")).isFalse();
    }

    @Test
    void shouldReturnChannelId() {
        final Channel channel = ChannelMother.fake();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = AliasesMother.creditCardNumber();

        final VerificationContext context = builder.providedAlias(providedAlias).build();

        assertThat(context.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldReturnIdentity() {
        final Identity identity = IdentityMother.build();

        final VerificationContext context = builder.identity(identity).build();

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnIdvIdValue() {
        final Identity identity = IdentityMother.build();

        final VerificationContext context = builder.identity(identity).build();

        assertThat(context.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = ActivityMother.fake();

        final VerificationContext context = builder.activity(activity).build();

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityName() {
        final Activity activity = ActivityMother.fake();

        final VerificationContext context = builder.activity(activity).build();

        assertThat(context.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldReturnCreated() {
        final Instant created = Instant.now();

        final VerificationContext context = builder.created(created).build();

        assertThat(context.getCreated()).isEqualTo(created);
    }

    @Test
    void shouldReturnExpiry() {
        final Instant expiry = Instant.now();

        final VerificationContext context = builder.expiry(expiry).build();

        assertThat(context.getExpiry()).isEqualTo(expiry);
    }

    @Test
    void shouldReturnSequences() {
        final VerificationSequences sequences = mock(VerificationSequences.class);

        final VerificationContext context = builder.sequences(sequences).build();

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldReturnContainsSequenceWithNextMethodFromSequences() {
        final String methodName = "method-name";
        final VerificationSequences sequences = mock(VerificationSequences.class);
        given(sequences.hasSequencesWithNextMethod(methodName)).willReturn(true);

        final VerificationContext context = builder.sequences(sequences).build();

        assertThat(context.containsSequenceWithNextMethod(methodName)).isTrue();
    }

    @Test
    void shouldAttemptToAddResultToAllSequences() {
        final VerificationSequences sequences = mock(VerificationSequences.class);
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        final VerificationContext context = builder.sequences(sequences).build();

        context.addResult(result);

        verify(sequences).addResultIfHasSequencesWithNextMethod(result);
    }

    @Test
    void shouldReturnContainsCompleteMethodFromSequences() {
        final String methodName = "method-name";
        final boolean expectedResult = true;
        final VerificationSequences sequences = mock(VerificationSequences.class);
        given(sequences.containsCompleteMethod(methodName)).willReturn(expectedResult);
        final VerificationContext context = builder.sequences(sequences).build();

        final boolean result = context.containsCompleteMethod(methodName);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnContainsCompleteSequenceContainingMethodFromSequences() {
        final String methodName = "method-name";
        final boolean expectedResult = true;
        final VerificationSequences sequences = mock(VerificationSequences.class);
        given(sequences.containsCompleteSequenceContainingMethod(methodName)).willReturn(expectedResult);
        final VerificationContext context = builder.sequences(sequences).build();

        final boolean result = context.containsCompleteSequenceContainingMethod(methodName);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnHasExpiredFalseIfCurrentTimeIsBeoreExpiry() {
        final Instant expiry = Instant.parse("2019-11-16T20:10:25.903264Z");
        final Instant currentTime = expiry.minus(Duration.ofHours(1));
        final VerificationContext context = builder.expiry(expiry).build();

        final boolean expired = context.hasExpired(currentTime);

        assertThat(expired).isFalse();
    }

    @Test
    void shouldReturnHasExpiredTrueIfCurrentTimeIsAfterExpiry() {
        final Instant expiry = Instant.parse("2019-11-16T20:10:25.903264Z");
        final Instant currentTime = expiry.plus(Duration.ofHours(1));
        final VerificationContext context = builder.expiry(expiry).build();

        final boolean expired = context.hasExpired(currentTime);

        assertThat(expired).isTrue();
    }

    @Test
    void shouldReturnNextOneTimePasscodeEligibleMethod() {
        final OneTimePasscode expectedMethod = OneTimePasscodeMother.eligible();
        final VerificationSequences sequences = mock(VerificationSequences.class);
        given(sequences.getNextEligibleMethod(expectedMethod.getName())).willReturn(expectedMethod);
        final VerificationContext context = builder.sequences(sequences).build();

        final OneTimePasscode method = context.getNextOneTimePasscodeEligibleMethod();

        assertThat(method).isEqualTo(expectedMethod);
    }

}
