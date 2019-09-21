package uk.co.mruoc.idv.identity.domain.model;

import java.util.UUID;

public class FakeIdentity extends Identity {

    public FakeIdentity() {
        this(UUID.randomUUID());
    }

    public FakeIdentity(final UUID idvIdValue) {
        super(buildAliases(idvIdValue));
    }

    private static Aliases buildAliases(final UUID idvIdValue) {
        return Aliases.with(
                new FakeIdvId(idvIdValue),
                new FakeCreditCardNumber()
        );
    }

}
