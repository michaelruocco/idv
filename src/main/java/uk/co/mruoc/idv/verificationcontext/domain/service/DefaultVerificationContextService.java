package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.time.Instant;
import java.util.UUID;

@Builder
public class DefaultVerificationContextService implements VerificationContextService {

    private final IdGenerator idGenerator;
    private final TimeService timeService;
    private final IdentityService identityService;
    private final SequenceLoader sequenceLoader;
    private final ExpiryCalculator expiryCalculator;
    private final VerificationContextDao dao;

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        final Identity identity = loadIdentity(request);

        final Activity activity = request.getActivity();
        final VerificationSequences sequences = loadVerificationSequences(request, identity);

        final UUID id = idGenerator.generate();
        final Instant created = timeService.now();
        final Instant expiry = calculateExpiry(request, created, sequences);

        final VerificationContext context = VerificationContext.builder()
                .id(id)
                .channel(request.getChannel())
                .providedAlias(request.getProvidedAlias())
                .activity(activity)
                .identity(identity)
                .created(created)
                .sequences(sequences)
                .expiry(expiry)
                .build();

        dao.save(context);

        return context;
    }

    @Override
    public VerificationContext get(final GetContextRequest request) {
        return load(request.getId());
    }

    @Override
    public VerificationContext updateResults(final UpdateContextResultRequest request) {
        final VerificationContext context = load(request.getContextId());
        final VerificationResult result = request.getResult();
        final VerificationContext updatedContext = context.addResult(result);
        dao.save(updatedContext);
        return updatedContext;
    }

    private Identity loadIdentity(final CreateContextRequest createContextRequest) {
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channel(createContextRequest.getChannel())
                .providedAlias(createContextRequest.getProvidedAlias())
                .build();
        return identityService.upsert(request);
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

    private VerificationContext load(final UUID id) {
        return dao.load(id);
    }

}
