package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.VerificationAttempts;

import java.util.UUID;

public class FakeVerificationAttemptsLoader implements VerificationAttemptsLoader {

    private VerificationAttempts attemptsToLoad;
    private UUID lastLoadedIdvIdValue;

    @Override
    public VerificationAttempts load(final UUID idvId) {
        this.lastLoadedIdvIdValue = idvId;
        return attemptsToLoad;
    }

    public void setAttemptsToLoad(final VerificationAttempts attempts) {
        this.attemptsToLoad = attempts;
    }

    public UUID getLastLoadedIdvIdValue() {
        return lastLoadedIdvIdValue;
    }

}
