package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Slf4j
@RequiredArgsConstructor
public class MongoIndexResolver {

    private final MongoTemplate template;
    private final IndexResolver resolver;

    public MongoIndexResolver(final MongoTemplate template, final MongoMappingContext mappingContext) {
        this(template, new MongoPersistentEntityIndexResolver(mappingContext));
    }

    public void resolve() {
        resolveIdentityIndicies();
    }

    private void resolveIdentityIndicies() {
        log.info("resolving indicies for {}", IdentityDocument.class);
        final IndexOperations indexOps = template.indexOps(IdentityDocument.class);
        resolver.resolveIndexFor(IdentityDocument.class).forEach(indexOps::ensureIndex);
    }

}
