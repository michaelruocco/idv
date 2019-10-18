package uk.co.mruoc.idv.verificationcontext.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdentity;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext.VerificationContextBuilder;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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
        final Channel channel = new FakeChannel();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnHasChannelIdTrueIfChannelIdMatches() {
        final Channel channel = new FakeChannel();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.hasChannelId("fake-channel")).isTrue();
    }

    @Test
    void shouldReturnHasChannelIFalseIfChannelIdDoesNotMatch() {
        final Channel channel = new FakeChannel();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.hasChannelId("other-channel")).isFalse();
    }

    @Test
    void shouldReturnChannelId() {
        final Channel channel = new FakeChannel();

        final VerificationContext context = builder.channel(channel).build();

        assertThat(context.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldReturnProvidedAlias() {
        final Alias providedAlias = new FakeCreditCardNumber();

        final VerificationContext context = builder.providedAlias(providedAlias).build();

        assertThat(context.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldReturnIdentity() {
        final Identity identity = new FakeIdentity();

        final VerificationContext context = builder.identity(identity).build();

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnIdvIdValue() {
        final Identity identity = new FakeIdentity();

        final VerificationContext context = builder.identity(identity).build();

        assertThat(context.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = new FakeActivity(Instant.now());

        final VerificationContext context = builder.activity(activity).build();

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnActivityName() {
        final Activity activity = new FakeActivity(Instant.now());

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
    void shouldAddResult() {
        final String methodName = "method-name";
        final VerificationSequences sequences = mock(VerificationSequences.class);
        final VerificationSequences updatedSequences = mock(VerificationSequences.class);
        final VerificationResult result = new FakeVerificationResultSuccessful(methodName);
        given(sequences.addResultIfHasSequencesWithNextMethod(result)).willReturn(updatedSequences);
        final VerificationContext context = builder.sequences(sequences).build();

        final VerificationContext updatedContext = context.addResult(result);

        assertThat(updatedContext).isEqualToIgnoringGivenFields(context, "sequences");
        assertThat(updatedContext.getSequences()).isEqualTo(updatedSequences);
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

}