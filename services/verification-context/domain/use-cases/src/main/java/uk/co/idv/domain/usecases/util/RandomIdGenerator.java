package uk.co.idv.domain.usecases.util;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
