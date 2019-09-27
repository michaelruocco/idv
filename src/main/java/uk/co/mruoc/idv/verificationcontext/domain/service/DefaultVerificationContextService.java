package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.lockout.domain.service.LoadLockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;
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
    private final LockoutService lockoutService;

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        final Identity identity = loadIdentity(request);
        validateLockoutState(request, identity);

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
        final VerificationContext context = loadContext(request.getId());
        validateLockoutState(context);
        return context;
    }

    @Override
    public VerificationContext updateResults(final UpdateContextResultRequest request) {
        final VerificationContext context = loadContext(request.getContextId());
        final VerificationResult result = request.getResult();
        final VerificationContext contextWithResult = context.addResult(result);
        recordAttempt(result, contextWithResult);
        dao.save(contextWithResult);
        return contextWithResult;
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

    private VerificationContext loadContext(final UUID id) {
        final VerificationContext context = dao.load(id).orElseThrow(() -> new VerificationContextNotFoundException(id));
        //TODO throw exception if expired
        validateLockoutState(context);
        return context;
    }

    private void recordAttempt(final VerificationResult result,
                                       final VerificationContext context) {
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .result(result)
                .context(context)
                .build();
        lockoutService.recordAttempt(request);
    }

    private void validateLockoutState(final CreateContextRequest createContextRequest,
                                          final Identity identity) {
        final LoadLockoutStateRequest request = LoadLockoutStateRequest.builder()
                .channel(createContextRequest.getChannel())
                .activity(createContextRequest.getActivity())
                .alias(createContextRequest.getProvidedAlias())
                .idvIdValue(identity.getIdvIdValue())
                .timestamp(timeService.now())
                .build();
        validateLockoutState(request);
    }

    private void validateLockoutState(final VerificationContext context) {
        final LoadLockoutStateRequest request = LoadLockoutStateRequest.builder()
                .channel(context.getChannel())
                .activity(context.getActivity())
                .alias(context.getProvidedAlias())
                .idvIdValue(context.getIdvIdValue())
                .timestamp(timeService.now())
                .build();
        validateLockoutState(request);
    }

    private void validateLockoutState(final LoadLockoutStateRequest request) {
        lockoutService.validateState(request);
    }

}
