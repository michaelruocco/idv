package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.UUID;

public class FakeVerificationContextService implements VerificationContextService {

    private VerificationContext context;

    private CreateContextRequest lastCreateRequest;
    private UUID lastLoadedId;
    private RecordResultRequest lastUpdateResultRequest;

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        this.lastCreateRequest = request;
        return context;
    }

    @Override
    public VerificationContext load(final UUID id) {
        this.lastLoadedId = id;
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

    public UUID getLastLoadedId() {
        return lastLoadedId;
    }

    public RecordResultRequest getLastUpdateResultRequest() {
        return lastUpdateResultRequest;
    }

}
