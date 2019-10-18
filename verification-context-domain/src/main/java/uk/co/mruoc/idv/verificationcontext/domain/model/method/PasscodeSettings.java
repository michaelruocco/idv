package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;

public interface PasscodeSettings {

    int getLength();

    Duration getDuration();

    int getMaxGenerationAttempts();

}
