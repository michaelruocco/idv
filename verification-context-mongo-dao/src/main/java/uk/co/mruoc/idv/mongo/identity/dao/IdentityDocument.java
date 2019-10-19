package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document("identities")
@CompoundIndex(def = "{'aliases.type':1, 'aliases.value':1}", name = "aliases-index", unique = true)
@Getter
@Builder
public class IdentityDocument {

    @Id
    private final String id;
    private final Collection<AliasDocument> aliases;

}
