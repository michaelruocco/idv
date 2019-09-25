package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

public class FakeVerificationContextService implements VerificationContextService {

    private VerificationContext context;

    private CreateContextRequest lastCreateRequest;
    private GetContextRequest lastGetRequest;
    private UpdateContextResultRequest lastUpdateResultRequest;

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        this.lastCreateRequest = request;
        return context;
    }

    @Override
    public VerificationContext get(final GetContextRequest request) {
        this.lastGetRequest = request;
        return context;
    }

    @Override
    public VerificationContext updateResult(final UpdateContextResultRequest request) {
        this.lastUpdateResultRequest = request;
        return context;
    }

    public void setContextToReturn(final VerificationContext context) {
        this.context = context;
    }

    public CreateContextRequest getLastCreateRequest() {
        return lastCreateRequest;
    }

    public GetContextRequest getLastGetRequest() {
        return lastGetRequest;
    }

    public UpdateContextResultRequest getLastUpdateResultRequest() {
        return lastUpdateResultRequest;
    }

}
