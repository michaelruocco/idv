package uk.co.mruoc.verificationcontext.domain.method;

import java.time.Duration;

public interface PasscodeSettings {

    int getLength();

    Duration getDuration();

    int getMaxAttempts();

}
