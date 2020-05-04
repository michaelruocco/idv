package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;


import java.time.Duration;

public class IneligiblePasscodeSettings extends DefaultPasscodeSettings {

    public IneligiblePasscodeSettings() {
        super(0, Duration.ZERO, 0);
    }

}
