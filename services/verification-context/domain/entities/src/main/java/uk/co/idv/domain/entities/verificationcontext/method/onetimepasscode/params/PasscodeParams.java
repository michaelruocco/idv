package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import java.time.Duration;

public interface PasscodeParams {

    int getLength();

    Duration getDuration();

    int getMaxDeliveries();

}
