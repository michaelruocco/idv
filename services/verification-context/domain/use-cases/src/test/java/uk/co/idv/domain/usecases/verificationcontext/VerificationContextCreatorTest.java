package uk.co.idv.domain.usecases.verificationcontext;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.usecases.util.id.FakeIdGenerator;
import uk.co.idv.domain.usecases.util.time.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.expiry.CalculateExpiryRequest;
import uk.co.idv.domain.usecases.verificationcontext.expiry.FakeExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.sequence.FakeSequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class VerificationContextCreatorTest {

    private static final UUID ID = UUID.randomUUID();
    private static final Instant NOW = Instant.now();
    private static final Duration EXPIRY_DURATION = Duration.ofMinutes(5);

    private final IdGenerator idGenerator = new FakeIdGenerator(ID);
    private final TimeProvider timeProvider = new FakeTimeProvider(NOW);

    private final FakeIdentityUpserter identityUpserter = new FakeIdentityUpserter();

    private final VerificationSequences sequences = mock(VerificationSequences.class);
    private final FakeSequenceLoader sequenceLoader = new FakeSequenceLoader(sequences);

    private final FakeExpiryCalculator expiryCalculator = new FakeExpiryCalculator(EXPIRY_DURATION);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextCreator creator = VerificationContextCreator.builder()
            .idGenerator(idGenerator)
            .timeProvider(timeProvider)
            .identityUpserter(identityUpserter)
            .sequenceLoader(sequenceLoader)
            .expiryCalculator(expiryCalculator)
            .dao(dao)
            .build();

    @Test
    void shouldPassRequestWhenUpsertingIdentity() {
        final CreateContextRequest request = CreateContextRequestMother.build();

        creator.create(request);

        assertThat(identityUpserter.getLastRequest()).isEqualTo(request);
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
        final Identity identity = IdentityMother.build();
        identityUpserter.setIdentityToReturn(identity);

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
        final Identity identity = IdentityMother.build();
        identityUpserter.setIdentityToReturn(identity);

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
