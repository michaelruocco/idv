package uk.co.mruoc.idv.domain.service;

import java.util.UUID;

public class FakeIdGenerator implements IdGenerator {

    private UUID idToGenerate;

    @Override
    public UUID generate() {
        return idToGenerate;
    }

    public void setIdToGenerate(final UUID idToGenerate) {
        this.idToGenerate = idToGenerate;
    }

}
