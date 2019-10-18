package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Slf4j
@Builder
public class IndiciesResolver {

    private final MongoTemplate template;
    private final MongoMappingContext mappingContext;

    public void resolve() {
        final IndexResolver resolver = new MongoPersistentEntityIndexResolver(mappingContext);
        resolveIdentityIndicies(resolver);
    }

    private void resolveIdentityIndicies(final IndexResolver resolver) {
        log.info("resolving indicies for {}", IdentityDocument.class);
        final IndexOperations indexOps = template.indexOps(IdentityDocument.class);
        resolver.resolveIndexFor(IdentityDocument.class).forEach(indexOps::ensureIndex);
    }

}
