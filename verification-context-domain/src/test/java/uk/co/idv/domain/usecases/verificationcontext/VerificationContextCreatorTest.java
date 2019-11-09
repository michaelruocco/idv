package uk.co.idv.domain.usecases.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.FakeActivity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.FakeChannel;
import uk.co.idv.domain.usecases.util.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.FakeTimeGenerator;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.usecases.identity.FakeIdentityService;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.idv.domain.usecases.lockout.FakeLockoutService;
import uk.co.idv.domain.usecases.lockout.LockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.LockoutStateValidator.LockedOutException;
import uk.co.idv.domain.entities.verificationcontext.FakeVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class VerificationContextCreatorTest {

    private static final UUID ID = UUID.randomUUID();
    private static final Instant NOW = Instant.now();
    private static final Duration EXPIRY_DURATION = Duration.ofMinutes(5);

    private final IdGenerator idGenerator = new FakeIdGenerator(ID);
    private final TimeGenerator timeGenerator = new FakeTimeGenerator(NOW);

    private final Identity identity = new Identity(AliasesMother.aliases());
    private final FakeIdentityService identityService = new FakeIdentityService(identity);

    private final VerificationSequences sequences = new FakeVerificationSequencesEligible();
    private final FakeSequenceLoader sequenceLoader = new FakeSequenceLoader(sequences);

    private final FakeExpiryCalculator expiryCalculator = new FakeExpiryCalculator(EXPIRY_DURATION);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final FakeLockoutService lockoutService = new FakeLockoutService();

    private final VerificationContextCreator creator = VerificationContextCreator.builder()
            .idGenerator(idGenerator)
            .timeGenerator(timeGenerator)
            .identityService(identityService)
            .sequenceLoader(sequenceLoader)
            .expiryCalculator(expiryCalculator)
            .lockoutService(lockoutService)
            .dao(dao)
            .build();

    @Test
    void shouldPassChannelWhenUpsertingIdentity() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        creator.create(createContextRequest);

        final UpsertIdentityRequest upsertIdentityRequest = identityService.getLastUpsertRequest();
        assertThat(upsertIdentityRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldPassProvidedAliasWhenUpsertingIdentity() {
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        creator.create(createContextRequest);

        final UpsertIdentityRequest upsertIdentityRequest = identityService.getLastUpsertRequest();
        assertThat(upsertIdentityRequest.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPassChannelWhenValidatingLockoutState() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldPassActivityWhenValidatingLockoutState() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .activity(activity)
                .build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldPassProvidedAliasWhenValidatingLockoutState() {
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPassIdvIdValueWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimestampWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getTimestamp()).isEqualTo(NOW);
    }

    @Test
    void shouldThrowExceptionIfLockoutStateIsLocked() {
        final LockoutState lockedState = mock(LockoutState.class);
        lockoutService.setHasLockedState(lockedState);
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        final LockedOutException error = (LockedOutException) catchThrowable(() -> creator.create(createContextRequest));

        assertThat(error).hasMessage("locked");
        assertThat(error.getLockoutState()).isEqualTo(lockedState);
    }


    @Test
    void shouldPassChannelWhenLoadingSequences() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldPassActivityWhenLoadingSequences() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .activity(activity)
                .build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldPassProvidedAliasWhenLoadingSequences() {
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPassIdentityWhenLoadingSequences() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPassChannelWhenCalculatingExpiry() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldPassActivityWhenCalculatingExpiry() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .activity(activity)
                .build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldPassCreatedTimestampWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPassSequenceWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPopulateId() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getId()).isEqualTo(ID);
    }

    @Test
    void shouldPopulateChannel() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest request = CreateContextRequest.builder()
                .channel(channel)
                .build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldPopulateProvidedAlias() {
        final Alias providedAlias = AliasesMother.creditCardNumber();
        final CreateContextRequest request = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPopulateActivity() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest request = CreateContextRequest.builder()
                .activity(activity)
                .build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldPopulateIdentity() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPopulateCreated() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateSequences() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPopulateExpiry() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getExpiry()).isEqualTo(NOW.plus(EXPIRY_DURATION));
    }

}
