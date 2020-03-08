package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;

import java.util.UUID;

public class FakeOneTimePasscodeService implements OneTimePasscodeService {

    private CreateOneTimePasscodeVerificationRequest lastCreateRequest;
    private UpdateOneTimePasscodeVerificationRequest lastUpdateRequest;
    private UUID lastLoadedId;

    private OneTimePasscodeVerification verificationToReturn;

    @Override
    public OneTimePasscodeVerification sendPasscode(final CreateOneTimePasscodeVerificationRequest request) {
        this.lastCreateRequest = request;
        return verificationToReturn;
    }

    @Override
    public OneTimePasscodeVerification sendPasscode(final UpdateOneTimePasscodeVerificationRequest request) {
        this.lastUpdateRequest = request;
        return verificationToReturn;
    }

    @Override
    public OneTimePasscodeVerification load(final UUID id) {
        this.lastLoadedId = id;
        return verificationToReturn;
    }

    public CreateOneTimePasscodeVerificationRequest getLastCreateRequest() {
        return lastCreateRequest;
    }

    public UpdateOneTimePasscodeVerificationRequest getLastUpdateRequest() {
        return lastUpdateRequest;
    }

    public UUID getLastLoadedId() {
        return lastLoadedId;
    }

    public void setVerificationToReturn(final OneTimePasscodeVerification verification) {
        this.verificationToReturn = verification;
    }

}
