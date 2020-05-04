package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;

import java.time.Duration;

public interface OneTimePasscodeParams extends VerificationMethodParams {

    PasscodeSettings getPasscodeSettings();

    int getPasscodeLength();

    int getMaxDeliveries();

    Duration getPasscodeDuration();

}
