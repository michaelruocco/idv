package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.time.Duration;
import java.util.Collections;

public class OneTimePasscodeSmsIneligible extends OneTimePasscodeSms {

    public OneTimePasscodeSmsIneligible() {
        super(new NoEligibleMobileNumbers(),
                Duration.ZERO,
                new IneligiblePasscodeSettings(),
                Collections.emptyList()
        );
    }

}
