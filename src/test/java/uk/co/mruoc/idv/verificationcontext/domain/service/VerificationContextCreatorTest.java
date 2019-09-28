package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.FakeActivity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.FakeChannel;
import uk.co.mruoc.idv.domain.service.FakeIdGenerator;
import uk.co.mruoc.idv.domain.service.FakeTimeService;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.FakeIdvId;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.FakeIdentityService;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.FakeLockoutService;
import uk.co.mruoc.idv.lockout.domain.service.LoadLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService.LockedOutException;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequencesEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

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
    private final TimeService timeService = new FakeTimeService(NOW);

    private final Identity identity = new Identity(Aliases.with(new FakeIdvId()));
    private final FakeIdentityService identityService = new FakeIdentityService(identity);

    private final VerificationSequences sequences = new FakeVerificationSequencesEligible();
    private final FakeSequenceLoader sequenceLoader = new FakeSequenceLoader(sequences);

    private final FakeExpiryCalculator expiryCalculator = new FakeExpiryCalculator(EXPIRY_DURATION);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final FakeLockoutService lockoutService = new FakeLockoutService();

    private final VerificationContextCreator creator = VerificationContextCreator.builder()
            .idGenerator(idGenerator)
            .timeService(timeService)
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
        final Alias providedAlias = new FakeCreditCardNumber();
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

        final LoadLockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldPassActivityWhenValidatingLockoutState() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .activity(activity)
                .build();

        creator.create(createContextRequest);

        final LoadLockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getActivityName()).isEqualTo(activity.getName());
    }

    @Test
    void shouldPassProvidedAliasWhenValidatingLockoutState() {
        final Alias providedAlias = new FakeCreditCardNumber();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        creator.create(createContextRequest);

        final LoadLockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPassIdvIdValueWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final LoadLockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimestampWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        creator.create(createContextRequest);

        final LoadLockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
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
        final Alias providedAlias = new FakeCreditCardNumber();
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
        final Alias providedAlias = new FakeCreditCardNumber();
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
