package uk.co.mruoc.idv.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

public class FakeVerificationAttemptsDao implements VerificationAttemptsDao {

    private VerificationAttempts attemptsToLoad;
    private VerificationAttempts lastSavedAttempts;
    private UUID lastLoadedIdvId;

    @Override
    public void save(final VerificationAttempts attempts) {
        this.lastSavedAttempts = attempts;
    }

    @Override
    public Optional<VerificationAttempts> load(final UUID idvId) {
        this.lastLoadedIdvId = idvId;
        return Optional.ofNullable(attemptsToLoad);
    }

    public void setAttemptsToLoad(final VerificationAttempts attemptsToLoad) {
        this.attemptsToLoad = attemptsToLoad;
    }

    public VerificationAttempts getLastSavedAttempts() {
        return lastSavedAttempts;
    }

    public UUID getLastLoadedIdvId() {
        return lastLoadedIdvId;
    }

}