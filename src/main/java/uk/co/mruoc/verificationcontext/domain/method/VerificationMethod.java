package uk.co.mruoc.verificationcontext.domain.method;

import java.time.Duration;

public interface VerificationMethod {

    String getName();

    Duration getDuration();

    boolean isEligible();

    Eligibility getEligibility();

    interface Names {

        String PUSH_NOTIFICATION = "PUSH_NOTIFICATION";
        String PHYSICAL_PINSENTRY = "PHYSICAL_PINSENTRY";
        String MOBILE_PINSENTRY = "MOBILE_PINSENTRY";
        String CARD_CREDENTIALS = "CARD_CREDENTIALS";
        String ONE_TIME_PASSCODE_SMS = "ONE_TIME_PASSCODE_SMS";

    }

}
