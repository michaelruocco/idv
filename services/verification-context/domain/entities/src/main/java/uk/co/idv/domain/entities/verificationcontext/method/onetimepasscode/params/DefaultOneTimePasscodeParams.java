package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class DefaultOneTimePasscodeParams implements OneTimePasscodeParams {

    private final PasscodeSettings passcodeSettings;
    private final int maxAttempts;
    private final Duration duration;

    @Override
    public int getPasscodeLength() {
        return passcodeSettings.getLength();
    }

    @Override
    public int getMaxDeliveries() {
        return passcodeSettings.getMaxDeliveries();
    }

    @Override
    public Duration getPasscodeDuration() {
        return passcodeSettings.getDuration();
    }

}
