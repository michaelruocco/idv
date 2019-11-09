package uk.co.idv.repository.mongo.verificationcontext;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.co.idv.repository.mongo.activity.ActivityDocument;
import uk.co.idv.repository.mongo.identity.alias.AliasDocument;
import uk.co.idv.repository.mongo.identity.IdentityDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocument;

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
