package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import java.time.Duration;

public interface PasscodeSettings {

    int getLength();

    Duration getDuration();

    int getMaxDeliveries();

}
