package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.expiry.CalculateExpiryRequest;
import uk.co.idv.domain.usecases.verificationcontext.expiry.ExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.expiry.MaxDurationExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.SequenceLoader;

import java.time.Instant;

@Builder
public class VerificationContextCreator {

    @Builder.Default
    private final IdGenerator idGenerator = new RandomIdGenerator();

    @Builder.Default
    private final TimeProvider timeProvider = new CurrentTimeProvider();

    @Builder.Default
    private final ExpiryCalculator expiryCalculator = new MaxDurationExpiryCalculator();

    private final IdentityUpserter identityUpserter;
    private final SequenceLoader sequenceLoader;
    private final VerificationContextDao dao;

    public VerificationContext create(final CreateContextRequest request) {
        final Identity identity = identityUpserter.upsertIdentity(request);

        final VerificationSequences sequences = loadVerificationSequences(request, identity);
        final VerificationContext context = buildContext(request, identity, sequences);
        dao.save(context);

        return context;
    }

    private VerificationSequences loadVerificationSequences(final CreateContextRequest createContextRequest,
                                                            final Identity identity) {
        final LoadSequencesRequest request = LoadSequencesRequest.builder()
                .channel(createContextRequest.getChannel())
                .activity(createContextRequest.getActivity())
                .providedAlias(createContextRequest.getProvidedAlias())
                .identity(identity)
                .build();
        return sequenceLoader.loadSequences(request);
    }

    private VerificationContext buildContext(final CreateContextRequest request,
                                             final Identity identity,
                                             final VerificationSequences sequences) {
        final Instant created = timeProvider.now();
        final Instant expiry = calculateExpiry(request, created, sequences);
        return VerificationContext.builder()
                .id(idGenerator.generate())
                .channel(request.getChannel())
                .providedAlias(request.getProvidedAlias())
                .activity(request.getActivity())
                .identity(identity)
                .created(created)
                .sequences(sequences)
                .expiry(expiry)
                .build();
    }

    private Instant calculateExpiry(final CreateContextRequest createContextRequest,
                                    final Instant created,
                                    final VerificationSequences sequences) {
        final CalculateExpiryRequest request = CalculateExpiryRequest.builder()
                .channel(createContextRequest.getChannel())
                .activity(createContextRequest.getActivity())
                .created(created)
                .sequences(sequences)
                .build();
        return expiryCalculator.calculateExpiry(request);
    }

}
