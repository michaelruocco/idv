package uk.co.mruoc.idv.identity.domain.model;

import java.util.UUID;

public class FakeIdvId extends IdvId {

    public FakeIdvId() {
        super(UUID.randomUUID());
    }

}
