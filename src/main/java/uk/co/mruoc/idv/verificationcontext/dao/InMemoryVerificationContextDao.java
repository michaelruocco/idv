package uk.co.mruoc.idv.verificationcontext.dao;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryVerificationContextDao implements VerificationContextDao {

    private final Map<UUID, VerificationContext> contexts = new HashMap<>();

    @Override
    public void save(final VerificationContext context) {
        contexts.put(context.getId(), context);
    }

    @Override
    public VerificationContext load(final UUID id) {
        if (contexts.containsKey(id)) {
            return contexts.get(id);
        }
        throw new VerificationContextNotFoundException(id);
    }
}
