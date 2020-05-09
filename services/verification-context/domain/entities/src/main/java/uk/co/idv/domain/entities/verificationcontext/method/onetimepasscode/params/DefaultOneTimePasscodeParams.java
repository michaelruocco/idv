package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Builder
@Data
public class DefaultOneTimePasscodeParams implements OneTimePasscodeParams {

    private final int maxAttempts;
    private final Duration duration;
    private final PasscodeParams passcodeParams;

    @Override
    public int getPasscodeLength() {
        return passcodeParams.getLength();
    }

    @Override
    public int getMaxDeliveries() {
        return passcodeParams.getMaxDeliveries();
    }

    @Override
    public Duration getPasscodeDuration() {
        return passcodeParams.getDuration();
    }

}
