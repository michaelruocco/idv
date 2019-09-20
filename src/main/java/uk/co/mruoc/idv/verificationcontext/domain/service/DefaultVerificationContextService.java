package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.identity.domain.service.UpsertIdentityRequest;
import uk.co.mruoc.idv.verificationcontext.domain.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Builder
public class DefaultVerificationContextService implements VerificationContextService {

    private final IdGenerator idGenerator;
    private final TimeService timeService;
    private final IdentityService identityService;
    private final SequenceLoader sequenceLoader;
    private final ExpiryCalculator expiryCalculator;
    private final VerificationContextDao dao;

    private final Map<UUID, VerificationContext> contexts = new HashMap<>();

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        final Channel channel = request.getChannel();
        final Alias providedAlias = request.getProvidedAlias();
        final Identity identity = loadIdentity(channel, providedAlias);

        final Activity activity = request.getActivity();
        final Collection<VerificationSequence> sequences = loadVerificationSequences(channel, activity, identity);

        final UUID id = idGenerator.generate();
        final Instant created = timeService.now();
        final Instant expiry = calculateExpiry(channel, activity, created, sequences);
        final VerificationContext context = VerificationContext.builder()
                .id(id)
                .channel(channel)
                .providedAlias(providedAlias)
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
        return dao.load(request.getId());
    }

    private Identity loadIdentity(final Channel channel, final Alias providedAlias) {
        final UpsertIdentityRequest request = UpsertIdentityRequest.builder()
                .channel(channel)
                .providedAlias(providedAlias)
                .build();
        return identityService.upsert(request);
    }

    private Collection<VerificationSequence> loadVerificationSequences(final Channel channel,
                                                                       final Activity activity,
                                                                       final Identity identity) {
        final LoadSequenceRequest request = LoadSequenceRequest.builder()
                .channel(channel)
                .activity(activity)
                .identity(identity)
                .build();
        return sequenceLoader.loadSequences(request);
    }

    private Instant calculateExpiry(final Channel channel,
                                    final Activity activity,
                                    final Instant created,
                                    final Collection<VerificationSequence> sequences) {
        final CalculateExpiryRequest request = CalculateExpiryRequest.builder()
                .channel(channel)
                .activity(activity)
                .created(created)
                .sequences(sequences)
                .build();
        return expiryCalculator.calculateExpiry(request);
    }

}
