package uk.co.idv.repository.mongo.verificationcontext.method.onetimepasscode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class OneTimePasscodeSmsDocument extends VerificationMethodDocument {

    private PasscodeSettingsDocument passcodeSettings;
    private Collection<MobileNumberDocument> mobileNumbers;

}
