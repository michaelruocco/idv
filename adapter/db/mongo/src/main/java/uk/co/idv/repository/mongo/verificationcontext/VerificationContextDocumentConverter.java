package uk.co.idv.repository.mongo.verificationcontext;

import lombok.Builder;
import uk.co.idv.repository.mongo.activity.ActivityDocumentConverterDelegator;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;
import uk.co.idv.repository.mongo.identity.IdentityDocumentConverter;
import uk.co.idv.repository.mongo.identity.alias.AliasDocumentConverter;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.time.Instant;
import java.util.UUID;

@Builder
public class VerificationContextDocumentConverter {

    private final ChannelDocumentConverterDelegator channelConverter;
    private final AliasDocumentConverter aliasConverter;
    private final IdentityDocumentConverter identityConverter;
    private final ActivityDocumentConverterDelegator activityConverter;
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
