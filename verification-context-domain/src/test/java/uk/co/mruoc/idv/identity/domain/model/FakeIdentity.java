package uk.co.mruoc.idv.identity.domain.model;

import java.util.UUID;

public class FakeIdentity extends Identity {

    public FakeIdentity() {
        this(UUID.randomUUID());
    }

    public FakeIdentity(final UUID idvIdValue) {
        super(new FakeAliases(idvIdValue));
    }

}
