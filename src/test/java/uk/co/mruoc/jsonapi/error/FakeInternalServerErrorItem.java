package uk.co.mruoc.jsonapi.error;

import java.util.UUID;

class FakeInternalServerErrorItem extends InternalServerErrorItem {

    private static final UUID ID = UUID.fromString("74a8a129-a96b-4979-98c1-90d864dd47a4");
    private static final String DETAIL = "faked detail message";

    FakeInternalServerErrorItem() {
        super(ID, DETAIL);
    }

}
