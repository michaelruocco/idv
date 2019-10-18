package uk.co.mruoc.idv.domain.service;

import java.util.UUID;

public class FakeIdGenerator implements IdGenerator {

    private final UUID id;

    public FakeIdGenerator(final UUID id) {
        this.id = id;
    }

    @Override
    public UUID generate() {
        return id;
    }

}
