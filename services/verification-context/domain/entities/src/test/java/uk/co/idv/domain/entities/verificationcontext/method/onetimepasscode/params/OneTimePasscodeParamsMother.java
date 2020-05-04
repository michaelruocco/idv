package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;

import java.time.Duration;

public class OneTimePasscodeParamsMother {

    private OneTimePasscodeParamsMother() {
        // utility class
    }

    public static OneTimePasscodeParams eligible() {
        return withMaxAttempts(1);
    }

    public static OneTimePasscodeParams withMaxAttempts(int maxAttempts) {
        return DefaultOneTimePasscodeParams.builder()
                .passcodeSettings(PasscodeSettingsMother.eligible())
                .duration(Duration.ofMinutes(5))
                .maxAttempts(maxAttempts)
                .build();
    }

    public static OneTimePasscodeParams ineligible() {
        return new IneligibleOneTimePasscodeParams();
    }
}
