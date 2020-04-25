package uk.co.idv.domain.usecases.verificationcontext;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.usecases.verificationcontext.result.RecordResultRequest;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;

import java.util.UUID;

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
    public VerificationContext load(final UUID id) {
        return loader.load(id);
    }

    @Override
    public VerificationContext recordResult(final RecordResultRequest request) {
        return resultRecorder.recordResult(request);
    }

}
