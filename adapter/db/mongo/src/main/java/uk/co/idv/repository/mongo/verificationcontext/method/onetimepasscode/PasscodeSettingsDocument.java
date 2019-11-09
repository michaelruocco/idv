package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import lombok.Data;


@Data
public class PasscodeSettingsDocument {

    private int length;
    private long duration;
    private int maxGenerationAttempts;

}
