package uk.co.idv.domain.usecases.util.id;

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
