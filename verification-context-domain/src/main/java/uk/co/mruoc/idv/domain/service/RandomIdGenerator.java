package uk.co.mruoc.idv.domain.service;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
