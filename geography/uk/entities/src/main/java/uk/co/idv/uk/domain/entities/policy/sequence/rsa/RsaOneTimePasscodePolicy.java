package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultOneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultPasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy.OneTimePasscodePolicy;

import java.time.Duration;

public class RsaOneTimePasscodePolicy extends OneTimePasscodePolicy {

    public RsaOneTimePasscodePolicy() {
        super(buildParams());
    }

    private static OneTimePasscodeParams buildParams() {
        return DefaultOneTimePasscodeParams.builder()
                .maxAttempts(5)
                .duration(Duration.ofMinutes(5))
                .passcodeParams(buildPasscodeParams())
                .build();
    }

    private static PasscodeParams buildPasscodeParams() {
        return DefaultPasscodeParams.builder()
                .length(8)
                .duration(Duration.ofSeconds(150))
                .maxDeliveries(3)
                .build();
    }

}
