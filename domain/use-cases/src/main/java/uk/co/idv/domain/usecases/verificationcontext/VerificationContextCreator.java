package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.lockout.LockoutStateRequest;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContextCreator {

    private final IdGenerator idGenerator;
    private final TimeGenerator timeGenerator;
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
        final Instant created = timeGenerator.now();
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
                .timestamp(timeGenerator.now())
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
