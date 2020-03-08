package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;

import java.util.UUID;

public interface OneTimePasscodeService {

    OneTimePasscodeVerification sendPasscode(CreateOneTimePasscodeVerificationRequest request);

    OneTimePasscodeVerification sendPasscode(UpdateOneTimePasscodeVerificationRequest request);

    OneTimePasscodeVerification load(UUID id);

}
