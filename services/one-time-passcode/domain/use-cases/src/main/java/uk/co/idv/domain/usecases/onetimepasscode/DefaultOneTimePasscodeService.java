package uk.co.idv.domain.usecases.onetimepasscode;

import lombok.Builder;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;

import java.util.UUID;

@Builder
public class DefaultOneTimePasscodeService implements OneTimePasscodeService {

    private final OneTimePasscodeSender sender;
    private final OneTimePasscodeVerificationLoader verificationLoader;
    private final OneTimePasscodeVerifier verifier;

    @Override
    public OneTimePasscodeVerification send(final SendOneTimePasscodeRequest request) {
        return sender.send(request);
    }

    @Override
    public OneTimePasscodeVerification send(final ResendOneTimePasscodeRequest request) {
        return sender.send(request);
    }

    @Override
    public OneTimePasscodeVerification verify(final VerifyOneTimePasscodeRequest request) {
        return verifier.verify(request);
    }

    @Override
    public OneTimePasscodeVerification load(final UUID id) {
        return verificationLoader.load(id);
    }

}
