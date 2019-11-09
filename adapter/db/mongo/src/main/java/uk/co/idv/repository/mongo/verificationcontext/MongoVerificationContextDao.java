package uk.co.idv.repository.mongo.verificationcontext;

import lombok.Builder;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.Optional;
import java.util.UUID;

@Builder
public class MongoVerificationContextDao implements VerificationContextDao {

    private final VerificationContextRepository repository;
    private final VerificationContextDocumentConverter converter;

    @Override
    public void save(final VerificationContext context) {
        final VerificationContextDocument document = converter.toDocument(context);
        repository.save(document);
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        final Optional<VerificationContextDocument> document = repository.findById(id.toString());
        return document.map(converter::toContext);
    }

}
