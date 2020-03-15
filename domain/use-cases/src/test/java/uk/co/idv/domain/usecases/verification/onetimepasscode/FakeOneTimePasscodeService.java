package uk.co.idv.domain.usecases.verification.onetimepasscode;

import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;

import java.util.UUID;

public class FakeOneTimePasscodeService implements OneTimePasscodeService {

    private SendOneTimePasscodeRequest lastCreateRequest;
    private ResendOneTimePasscodeRequest lastUpdateRequest;
    private VerifyOneTimePasscodeRequest lastVerifyRequest;
    private UUID lastLoadedId;

    private OneTimePasscodeVerification verificationToReturn;

    @Override
    public OneTimePasscodeVerification send(final SendOneTimePasscodeRequest request) {
        this.lastCreateRequest = request;
        return verificationToReturn;
    }

    @Override
    public OneTimePasscodeVerification send(final ResendOneTimePasscodeRequest request) {
        this.lastUpdateRequest = request;
        return verificationToReturn;
    }

    @Override
    public OneTimePasscodeVerification verify(final VerifyOneTimePasscodeRequest request) {
        this.lastVerifyRequest = request;
        return verificationToReturn;
    }

    @Override
    public OneTimePasscodeVerification load(final UUID id) {
        this.lastLoadedId = id;
        return verificationToReturn;
    }

    public SendOneTimePasscodeRequest getLastCreateRequest() {
        return lastCreateRequest;
    }

    public ResendOneTimePasscodeRequest getLastUpdateRequest() {
        return lastUpdateRequest;
    }

    public VerifyOneTimePasscodeRequest getLastVerifyRequest() {
        return lastVerifyRequest;
    }

    public UUID getLastLoadedId() {
        return lastLoadedId;
    }

    public void setVerificationToReturn(final OneTimePasscodeVerification verification) {
        this.verificationToReturn = verification;
    }

}
