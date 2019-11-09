package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocument;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocument;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocument;

import java.util.Collection;


@Document("verificationContexts")
@Data
public class VerificationContextDocument {

    @Id
    private String id;
    private ChannelDocument channel;
    private AliasDocument providedAlias;
    private IdentityDocument identity;
    private ActivityDocument activity;
    private String created;
    private String expiry;
    private Collection<VerificationSequenceDocument> sequences;

}
