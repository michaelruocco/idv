package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverterDelegator;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityConverter;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContextConverter {

    private final ChannelConverter channelConverter;
    private final AliasConverter aliasConverter;
    private final IdentityConverter identityConverter;
    private final ActivityConverterDelegator activityConverter;

    public VerificationContext toContext(final VerificationContextDocument document) {
        return VerificationContext.builder()
                .id(UUID.fromString(document.getId()))
                .channel(channelConverter.toChannel(document.getChannel()))
                .providedAlias(aliasConverter.toAlias(document.getProvidedAlias()))
                .identity(identityConverter.toIdentity(document.getIdentity()))
                .activity(activityConverter.toActivity(document.getActivity()))
                .created(Instant.parse(document.getCreated()))
                .expiry(Instant.parse(document.getExpiry()))
                .sequences(new VerificationSequences()) //TODO add sequences converter
                .build();
    }

    public VerificationContextDocument toDocument(final VerificationContext context) {
        return VerificationContextDocument.builder()
                .id(context.getId().toString())
                .channel(channelConverter.toDocument(context.getChannel()))
                .providedAlias(aliasConverter.toDocument(context.getProvidedAlias()))
                .identity(identityConverter.toDocument(context.getIdentity()))
                .activity(activityConverter.toDocument(context.getActivity()))
                .created(context.getCreated().toString())
                .expiry(context.getExpiry().toString())
                //TODO add sequences converter
                .build();
    }

}
