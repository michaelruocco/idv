package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;


import java.time.Duration;

public class IneligiblePasscodeParams extends DefaultPasscodeParams {

    public IneligiblePasscodeParams() {
        super(0, Duration.ZERO, 0);
    }

}
