package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

public class FakeVerificationContextService implements VerificationContextService {

    private VerificationContext context;

    private CreateContextRequest lastCreateRequest;
    private LoadContextRequest lastGetRequest;
    private RecordResultRequest lastUpdateResultRequest;

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        this.lastCreateRequest = request;
        return context;
    }

    @Override
    public VerificationContext load(final LoadContextRequest request) {
        this.lastGetRequest = request;
        return context;
    }

    @Override
    public VerificationContext recordResult(final RecordResultRequest request) {
        this.lastUpdateResultRequest = request;
        return context;
    }

    public void setContextToReturn(final VerificationContext context) {
        this.context = context;
    }

    public CreateContextRequest getLastCreateRequest() {
        return lastCreateRequest;
    }

    public LoadContextRequest getLastGetRequest() {
        return lastGetRequest;
    }

    public RecordResultRequest getLastUpdateResultRequest() {
        return lastUpdateResultRequest;
    }

}
