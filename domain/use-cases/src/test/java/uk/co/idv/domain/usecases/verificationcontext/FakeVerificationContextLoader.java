package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.UUID;

public class FakeVerificationContextLoader implements VerificationContextLoader {

    private VerificationContext contextToLoad;
    private UUID lastLoadedContextId;

    @Override
    public VerificationContext load(final UUID id) {
        this.lastLoadedContextId = id;
        return contextToLoad;
    }

    public UUID getLastLoadedContextId() {
        return lastLoadedContextId;
    }

    public void setContextToLoad(final VerificationContext context) {
        this.contextToLoad = context;
    }

}
