package uk.co.idv.repository.mongo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import uk.co.idv.repository.mongo.identity.IdentityDocument;

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
        resolveIndicies(IdentityDocument.class);
    }

    private void resolveIndicies(final Class<?> type) {
        log.info("resolving indicies for {}", type);
        final IndexOperations indexOps = template.indexOps(type);
        resolver.resolveIndexFor(type).forEach(indexOps::ensureIndex);
    }

}
