package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

public class FakeVerificationContextLoader implements VerificationContextLoader {

    private VerificationContext contextToLoad;
    private LoadContextRequest lastLoadContextRequest;

    @Override
    public VerificationContext load(final LoadContextRequest request) {
        this.lastLoadContextRequest = request;
        return contextToLoad;
    }

    public LoadContextRequest getLastLoadContextRequest() {
        return lastLoadContextRequest;
    }

    public void setContextToLoad(final VerificationContext context) {
        this.contextToLoad = context;
    }

}
