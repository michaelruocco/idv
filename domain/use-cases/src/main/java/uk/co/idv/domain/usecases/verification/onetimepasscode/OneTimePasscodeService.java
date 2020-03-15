package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;

import java.util.UUID;

public interface OneTimePasscodeService {

    OneTimePasscodeVerification send(SendOneTimePasscodeRequest request);

    OneTimePasscodeVerification send(ResendOneTimePasscodeRequest request);

    OneTimePasscodeVerification verify(VerifyOneTimePasscodeRequest request);

    OneTimePasscodeVerification load(UUID id);

}
