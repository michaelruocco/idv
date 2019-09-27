package uk.co.mruoc.idv.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryVerificationAttemptsDao implements VerificationAttemptsDao {

    private final Map<UUID, VerificationAttempts> attemptsMap = new HashMap<>();

    @Override
    public void save(final VerificationAttempts attempts) {
        attemptsMap.put(attempts.getIdvId(), attempts);
    }

    @Override
    public Optional<VerificationAttempts> load(final UUID idvId) {
        return Optional.ofNullable(attemptsMap.get(idvId));
    }

}
