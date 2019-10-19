package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocument;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocument;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocument;

import java.util.Collection;


@Document("verificationContexts")
@Getter
@Builder
public class VerificationContextDocument {

    @Id
    private final String id;
    private final ChannelDocument channel;
    private final AliasDocument providedAlias;
    private final IdentityDocument identity;
    private final ActivityDocument activity;
    private final String created;
    private final String expiry;
    private final Collection<VerificationSequenceDocument> sequences;

}
