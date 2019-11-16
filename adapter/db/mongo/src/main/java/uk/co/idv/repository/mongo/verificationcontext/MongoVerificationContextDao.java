package uk.co.idv.repository.mongo.verificationcontext;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class MongoVerificationContextDao implements VerificationContextDao {

    private final VerificationContextRepository repository;
    private final VerificationContextDocumentConverter converter;

    @Override
    public void save(final VerificationContext context) {
        final VerificationContextDocument document = converter.toDocument(context);
        log.info("saving verification context {}", document);
        repository.save(document);
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        final Optional<VerificationContextDocument> document = repository.findById(id.toString());
        log.info("loaded verification context {} from id {}", document, id);
        return document.map(converter::toContext);
    }

}
