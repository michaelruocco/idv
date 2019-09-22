package uk.co.mruoc.idv.identity.domain.model;

import java.util.Arrays;
import java.util.UUID;

public class FakeAliases extends Aliases {

    public FakeAliases(final UUID idvIdValue) {
        super(Arrays.asList(
                new FakeIdvId(idvIdValue),
                new FakeCreditCardNumber()
        ));
    }

}
