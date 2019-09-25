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
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.FakeIdentityService;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequencesEligible;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultVerificationContextServiceTest {

    private static final UUID ID = UUID.randomUUID();
    private static final Instant NOW = Instant.now();
    private static final Duration EXPIRY_DURATION = Duration.ofMinutes(5);

    private final IdGenerator idGenerator = new FakeIdGenerator(ID);
    private final TimeService timeService = new FakeTimeService(NOW);

    private final Identity identity = new Identity(Aliases.empty());
    private final FakeIdentityService identityService = new FakeIdentityService(identity);

    private final VerificationSequences sequences = new FakeVerificationSequencesEligible();
    private final FakeSequenceLoader sequenceLoader = new FakeSequenceLoader(sequences);

    private final FakeExpiryCalculator expiryCalculator = new FakeExpiryCalculator(EXPIRY_DURATION);
    private final VerificationContextDao dao = mock(VerificationContextDao.class);

    private final VerificationContextService contextService = DefaultVerificationContextService.builder()
            .idGenerator(idGenerator)
            .timeService(timeService)
            .identityService(identityService)
            .sequenceLoader(sequenceLoader)
            .expiryCalculator(expiryCalculator)
            .dao(dao)
            .build();

    @Test
    void shouldPopulateId() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getId()).isEqualTo(ID);
    }

    @Test
    void shouldPopulateChannel() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest request = CreateContextRequest.builder()
                .channel(channel)
                .build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getChannelId()).isEqualTo(channel.getId());
    }

    @Test
    void shouldPopulateProvidedAlias() {
        final Alias providedAlias = new FakeCreditCardNumber();
        final CreateContextRequest request = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPopulateActivity() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest request = CreateContextRequest.builder()
                .activity(activity)
                .build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldPopulateIdentity() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPassChannelWhenUpsertingIdentity() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        contextService.create(createContextRequest);

        final UpsertIdentityRequest upsertIdentityRequest = identityService.getLastUpsertRequest();
        assertThat(upsertIdentityRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldPassProvidedAliasWhenUpsertingIdentity() {
        final Alias providedAlias = new FakeCreditCardNumber();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        contextService.create(createContextRequest);

        final UpsertIdentityRequest upsertIdentityRequest = identityService.getLastUpsertRequest();
        assertThat(upsertIdentityRequest.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPopulateCreated() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPopulateSequences() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldPassChannelWhenLoadingSequences() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        contextService.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldPassActivityWhenLoadingSequences() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .activity(activity)
                .build();

        contextService.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldPassProvidedAliasWhenLoadingSequences() {
        final Alias providedAlias = new FakeCreditCardNumber();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .providedAlias(providedAlias)
                .build();

        contextService.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getProvidedAlias()).isEqualTo(providedAlias);
    }

    @Test
    void shouldPassIdentityWhenLoadingSequences() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        contextService.create(createContextRequest);

        final LoadSequencesRequest loadSequencesRequest = sequenceLoader.getLastRequest();
        assertThat(loadSequencesRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPopulateExpiry() {
        final CreateContextRequest request = CreateContextRequest.builder().build();

        final VerificationContext context = contextService.create(request);

        assertThat(context.getExpiry()).isEqualTo(NOW.plus(EXPIRY_DURATION));
    }

    @Test
    void shouldPassChannelWhenCalculatingExpiry() {
        final Channel channel = new FakeChannel();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .channel(channel)
                .build();

        contextService.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldPassActivityWhenCalculatingExpiry() {
        final Activity activity = new FakeActivity();
        final CreateContextRequest createContextRequest = CreateContextRequest.builder()
                .activity(activity)
                .build();

        contextService.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldPassCreatedTimestampWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        contextService.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getCreated()).isEqualTo(NOW);
    }

    @Test
    void shouldPassSequenceWhenCalculatingExpiry() {
        final CreateContextRequest createContextRequest = CreateContextRequest.builder().build();

        contextService.create(createContextRequest);

        final CalculateExpiryRequest calculateExpiryRequest = expiryCalculator.getLastRequest();
        assertThat(calculateExpiryRequest.getSequences()).isEqualTo(sequences);
    }

    @Test
    void shouldLoadCreatedContext() {
        final UUID id = UUID.randomUUID();
        final VerificationContext context = mock(VerificationContext.class);
        when(dao.load(id)).thenReturn(context);
        final GetContextRequest request = GetContextRequest.builder()
                .id(id)
                .build();

        final VerificationContext loadedContext = contextService.get(request);

        assertThat(loadedContext).isEqualTo(context);
    }

    @Test
    void shouldAddResultToContext() {
        final UUID id = UUID.randomUUID();
        final VerificationContext context = mock(VerificationContext.class);
        when(dao.load(id)).thenReturn(context);
        final VerificationContext expectedUpdatedContext = mock(VerificationContext.class);
        final VerificationResult result = new FakeVerificationResultSuccessful("method-name");
        when(context.addResult(result)).thenReturn(expectedUpdatedContext);
        final UpdateContextResultRequest updateResultRequest = UpdateContextResultRequest.builder()
                .contextId(id)
                .result(result)
                .build();

        final VerificationContext updatedContext = contextService.updateResult(updateResultRequest);

        verify(dao).save(updatedContext);
        assertThat(updatedContext).isEqualTo(expectedUpdatedContext);
    }

}
