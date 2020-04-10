package uk.co.idv.beantest;

import org.meanbean.lang.Factory;

import java.util.UUID;

public class UuidFactory implements Factory<UUID> {

    @Override
    public UUID create() {
        return UUID.randomUUID();
    }

}
