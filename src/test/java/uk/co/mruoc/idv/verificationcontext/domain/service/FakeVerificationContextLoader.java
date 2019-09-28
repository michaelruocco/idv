package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

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
