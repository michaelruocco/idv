package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.identity.Identity;

public class FakeIdentityUpserter implements IdentityUpserter {

    private CreateContextRequest lastRequest;
    private Identity identityToReturn;

    @Override
    public Identity upsertIdentity(final CreateContextRequest request) {
        this.lastRequest = request;
        return identityToReturn;
    }

    public void setIdentityToReturn(final Identity identity) {
        this.identityToReturn = identity;
    }

    public CreateContextRequest getLastRequest() {
        return lastRequest;
    }

}
