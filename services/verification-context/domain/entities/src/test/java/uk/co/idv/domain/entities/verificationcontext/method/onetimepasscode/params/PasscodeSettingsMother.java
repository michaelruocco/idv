package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params;


import java.time.Duration;

public class PasscodeSettingsMother {

    private PasscodeSettingsMother() {
        // utility class
    }

    public static PasscodeSettings eligible() {
        return DefaultPasscodeSettings.builder()
                .length(8)
                .duration(Duration.ofMillis(150000))
                .maxDeliveries(3)
                .build();
    }

    public static PasscodeSettings ineligible() {
        return DefaultPasscodeSettings.builder()
                .length(0)
                .duration(Duration.ZERO)
                .maxDeliveries(0)
                .build();
    }

}
