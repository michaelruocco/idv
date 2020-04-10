package uk.co.idv.repository.inmemory.verificationcontext;

import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryVerificationContextDao implements VerificationContextDao {

    private final Map<UUID, VerificationContext> contexts = new HashMap<>();

    @Override
    public void save(final VerificationContext context) {
        contexts.put(context.getId(), context);
    }

    @Override
    public Optional<VerificationContext> load(final UUID id) {
        return Optional.ofNullable(contexts.get(id));
    }

}
