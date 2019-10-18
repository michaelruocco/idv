package uk.co.mruoc.idv.identity.domain.model;

import java.util.UUID;

public class FakeIdvId extends IdvId {

    public FakeIdvId() {
        this(UUID.randomUUID());
    }

    public FakeIdvId(final UUID idValue) {
        super(idValue);
    }

}
