package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverterDelegator;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContextConverter {

    private final ChannelConverter channelConverter;
    private final AliasConverter aliasConverter;
    private final IdentityConverter identityConverter;
    private final ActivityConverterDelegator activityConverter;
    private final VerificationSequencesConverter sequencesConverter;

    public VerificationContext toContext(final VerificationContextDocument document) {
        return VerificationContext.builder()
                .id(UUID.fromString(document.getId()))
                .channel(channelConverter.toChannel(document.getChannel()))
                .providedAlias(aliasConverter.toAlias(document.getProvidedAlias()))
                .identity(identityConverter.toIdentity(document.getIdentity()))
                .activity(activityConverter.toActivity(document.getActivity()))
                .created(Instant.parse(document.getCreated()))
                .expiry(Instant.parse(document.getExpiry()))
                .sequences(sequencesConverter.toSequences(document.getSequences()))
                .build();
    }

    public VerificationContextDocument toDocument(final VerificationContext context) {
        final VerificationContextDocument document = new VerificationContextDocument();
        document.setId(context.getId().toString());
        document.setChannel(channelConverter.toDocument(context.getChannel()));
        document.setProvidedAlias(aliasConverter.toDocument(context.getProvidedAlias()));
        document.setIdentity(identityConverter.toDocument(context.getIdentity()));
        document.setActivity(activityConverter.toDocument(context.getActivity()));
        document.setCreated(context.getCreated().toString());
        document.setExpiry(context.getExpiry().toString());
        document.setSequences(sequencesConverter.toDocuments(context.getSequences()));
        return document;
    }

}
