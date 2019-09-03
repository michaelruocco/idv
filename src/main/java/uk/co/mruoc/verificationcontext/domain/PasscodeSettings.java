package uk.co.mruoc.verificationcontext.domain;

import java.time.Duration;

public interface PasscodeSettings {

    int getLength();

    Duration getDuration();

    int getMaxAttempts();

}
