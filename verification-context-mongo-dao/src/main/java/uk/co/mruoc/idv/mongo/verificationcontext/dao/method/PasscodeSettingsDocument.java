package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Data;


@Data
public class PasscodeSettingsDocument {

    private int length;
    private String duration;
    private int maxGenerationAttempts;

}
