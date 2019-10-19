package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MongoVerificationContextDao implements VerificationContextDao {

    private final VerificationContextRepository repository;
    private final VerificationContextConverter converter;

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
