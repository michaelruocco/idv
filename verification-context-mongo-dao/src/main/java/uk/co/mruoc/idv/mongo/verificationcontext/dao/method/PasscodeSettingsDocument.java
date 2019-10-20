package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Data;


@Data
public class PasscodeSettingsDocument {

    private int length;
    private long duration;
    private int maxGenerationAttempts;

}
