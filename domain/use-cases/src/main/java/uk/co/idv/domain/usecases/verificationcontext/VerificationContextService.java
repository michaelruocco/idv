package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.UUID;

public interface VerificationContextService {

    VerificationContext create(final CreateContextRequest request);

    VerificationContext load(final UUID id);

    VerificationContext recordResult(final RecordResultRequest request);

}
