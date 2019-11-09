package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class OneTimePasscodeSmsDocument extends VerificationMethodDocument {

    private PasscodeSettingsDocument passcodeSettings;
    private Collection<MobileNumberDocument> mobileNumbers;

}
