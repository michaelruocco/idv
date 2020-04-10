package uk.co.idv.repository.inmemory.lockout;

import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryVerificationAttemptDao implements VerificationAttemptDao {

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
