package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

@Builder
@Getter
public class RecordAttemptRequest {

    private final VerificationResult result;
    private final VerificationContext context;

}
