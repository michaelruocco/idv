package uk.co.idv.domain.usecases.identity;

import uk.co.idv.domain.entities.identity.Identity;

public class FakeIdentityService implements IdentityService {

    private UpsertIdentityRequest lastUpsertRequest;
    private LoadIdentityRequest lastLoadRequest;

    private final Identity identity;

    public FakeIdentityService(final Identity identity) {
        this.identity = identity;
    }

    @Override
    public Identity upsert(final UpsertIdentityRequest request) {
        this.lastUpsertRequest = request;
        return identity;
    }

    @Override
    public Identity load(final LoadIdentityRequest request) {
        this.lastLoadRequest = request;
        return identity;
    }

    public UpsertIdentityRequest getLastUpsertRequest() {
        return lastUpsertRequest;
    }

    public LoadIdentityRequest getLastLoadRequest() {
        return lastLoadRequest;
    }

}
