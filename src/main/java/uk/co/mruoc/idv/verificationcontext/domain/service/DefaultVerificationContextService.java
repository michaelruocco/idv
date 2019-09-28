package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

@Builder
public class DefaultVerificationContextService implements VerificationContextService {

    private final VerificationContextCreator creator;
    private final VerificationContextLoader loader;
    private final VerificationContextResultRecorder resultRecorder;

    @Override
    public VerificationContext create(final CreateContextRequest request) {
        return creator.create(request);
    }

    @Override
    public VerificationContext load(final LoadContextRequest request) {
        return loader.load(request);
    }

    @Override
    public VerificationContext recordResult(final RecordResultRequest request) {
        return resultRecorder.recordResult(request);
    }

}
