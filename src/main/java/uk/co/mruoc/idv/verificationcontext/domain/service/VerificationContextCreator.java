package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContextCreator {

    private final IdGenerator idGenerator;
    private final TimeService timeService;
    private final IdentityService identityService;
    private final SequenceLoader sequenceLoader;
    private final ExpiryCalculator expiryCalculator;
    private final LockoutService lockoutService;
    private final VerificationContextDao dao;

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

    private Identity loadIdentity(final CreateContextRequest createContextRequest) {
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channel(createContextRequest.getChannel())
                .providedAlias(createContextRequest.getProvidedAlias())
                .build();
        return identityService.upsert(request);
    }

    private void validateLockoutState(final CreateContextRequest createContextRequest, final Identity identity) {
        final LockoutStateRequest request = VerificationContextLoadLockoutStateRequest.builder()
                .channel(createContextRequest.getChannel())
                .activity(createContextRequest.getActivity())
                .alias(createContextRequest.getProvidedAlias())
                .idvIdValue(identity.getIdvIdValue())
                .timestamp(timeService.now())
                .build();
        lockoutService.validateState(request);
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

}
