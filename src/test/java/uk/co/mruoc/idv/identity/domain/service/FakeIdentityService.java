package uk.co.mruoc.idv.identity.domain.service;

import uk.co.mruoc.idv.identity.domain.model.Identity;

public class FakeIdentityService implements IdentityService {

    private UpsertIdentityRequest lastUpsertRequest;

    private final Identity identity;

    public FakeIdentityService(final Identity identity) {
        this.identity = identity;
    }

    @Override
    public Identity upsert(final UpsertIdentityRequest request) {
        this.lastUpsertRequest = request;
        return identity;
    }

    public UpsertIdentityRequest getLastUpsertRequest() {
        return lastUpsertRequest;
    }

}
