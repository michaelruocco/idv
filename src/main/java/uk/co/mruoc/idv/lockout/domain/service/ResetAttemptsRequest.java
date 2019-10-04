package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

@Builder
@Getter
public class ResetAttemptsRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final VerificationAttempts attempts;

}
