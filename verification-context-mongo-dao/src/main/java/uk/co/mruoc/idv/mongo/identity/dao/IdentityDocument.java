package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document("identities")
@CompoundIndex(def = "{'aliases.type':1, 'aliases.value':1}", name = "aliases-index", unique = true)
@Data
public class IdentityDocument {

    @Id
    private String id;
    private Collection<AliasDocument> aliases;

}
