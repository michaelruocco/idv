package uk.co.idv.domain.usecases.onetimepasscode;

import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.onetimepasscode.send.ResendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequest;

import java.util.UUID;

public interface OneTimePasscodeService {

    OneTimePasscodeVerification send(SendOneTimePasscodeRequest request);

    OneTimePasscodeVerification send(ResendOneTimePasscodeRequest request);

    OneTimePasscodeVerification verify(VerifyOneTimePasscodeRequest request);

    OneTimePasscodeVerification load(UUID id);

}
