package uk.co.idv.domain.usecases.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.util.id.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.time.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.usecases.identity.FakeIdentityService;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.usecases.lockout.FakeLockoutService;
import uk.co.idv.domain.usecases.lockout.LockoutStateValidator.LockedOutException;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.expiry.FakeExpiryCalculator;

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
    private final TimeProvider timeProvider = new FakeTimeProvider(NOW);

    private final Identity identity = IdentityMother.build();
    private final FakeIdentityService identityService = new FakeIdentityService(identity);

    private final VerificationSequences sequences = new StubVerificationSequencesEligible();
    private final FakeSequenceLoader sequenceLoader = new FakeSequenceLoader(sequences);

    private final FakeExpiryCalculator expiryCalculator = new FakeExpiryCalculator(EXPIRY_DURATION);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final FakeLockoutService lockoutService = new FakeLockoutService();

    private final VerificationContextCreator creator = VerificationContextCreator.builder()
            .idGenerator(idGenerator)
            .timeProvider(timeProvider)
            .identityService(identityService)
            .sequenceLoader(sequenceLoader)
            .expiryCalculator(expiryCalculator)
            .lockoutService(lockoutService)
            .dao(dao)
            .build();

    @Test
    void shouldPassChannelWhenUpsertingIdentity() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final UpsertIdentityRequest upsertIdentityRequest = identityService.getLastUpsertRequest();
        assertThat(upsertIdentityRequest.getChannel()).isEqualTo(createContextRequest.getChannel());
    }

    @Test
    void shouldPassProvidedAliasWhenUpsertingIdentity() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final UpsertIdentityRequest upsertIdentityRequest = identityService.getLastUpsertRequest();
        assertThat(upsertIdentityRequest.getProvidedAlias()).isEqualTo(createContextRequest.getProvidedAlias());
    }

    @Test
    void shouldPassChannelWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getChannelId()).isEqualTo(createContextRequest.getChannelId());
    }

    @Test
    void shouldPassActivityWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getActivityName()).isEqualTo(createContextRequest.getActivityName());
    }

    @Test
    void shouldPassProvidedAliasWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getAlias()).isEqualTo(createContextRequest.getProvidedAlias());
    }

    @Test
    void shouldPassIdvIdValueWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getIdvIdValue()).isEqualTo(identity.getIdvIdValue());
    }

    @Test
    void shouldPassCurrentTimestampWhenValidatingLockoutState() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LockoutStateRequest validateStateRequest = lockoutService.getLastValidateStateRequest();
        assertThat(validateStateRequest.getTimestamp()).isEqualTo(NOW);
    }

    @Test
    void shouldThrowExceptionIfLockoutStateIsLocked() {
        final LockoutState lockedState = mock(LockoutState.class);
        lockoutService.setHasLockedState(lockedState);
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        final LockedOutException error = (LockedOutException) catchThrowable(() -> creator.create(createContextRequest));

        assertThat(error).hasMessage("locked");
        assertThat(error.getLockoutState()).isEqualTo(lockedState);
    }


    @Test
    void shouldPassChannelWhenLoadingSequences() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getChannel()).isEqualTo(createContextRequest.getChannel());
    }

    @Test
    void shouldPassActivityWhenLoadingSequences() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getActivity()).isEqualTo(createContextRequest.getActivity());
    }

    @Test
    void shouldPassProvidedAliasWhenLoadingSequences() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getProvidedAlias()).isEqualTo(createContextRequest.getProvidedAlias());
    }

    @Test
    void shouldPassIdentityWhenLoadingSequences() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPassChannelWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getChannel()).isEqualTo(createContextRequest.getChannel());
    }

    @Test
    void shouldPassActivityWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getActivity()).isEqualTo(createContextRequest.getActivity());
    }

    @Test
    void shouldPassCreatedTimestampWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPassSequenceWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequestMother.build();

        creator.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPopulateId() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getId()).isEqualTo(ID);
    }

    @Test
    void shouldPopulateChannel() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getChannelId()).isEqualTo(request.getChannelId());
    }

    @Test
    void shouldPopulateProvidedAlias() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getProvidedAlias()).isEqualTo(request.getProvidedAlias());
    }

    @Test
    void shouldPopulateActivity() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getActivity()).isEqualTo(request.getActivity());
    }

    @Test
    void shouldPopulateIdentity() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPopulateCreated() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateSequences() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPopulateExpiry() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        final VerificationContext context = creator.create(request);

        assertThat(context.getExpiry()).isEqualTo(NOW.plus(EXPIRY_DURATION));
    }

}
