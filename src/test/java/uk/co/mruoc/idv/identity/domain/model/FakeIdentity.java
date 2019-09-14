package uk.co.mruoc.idv.identity.domain.model;

public class FakeIdentity extends Identity {

    public FakeIdentity() {
        super(buildAliases());
    }

    private static Aliases buildAliases() {
        return Aliases.with(
                new FakeIdvId(),
                new FakeCreditCardNumber()
        );
    }

}
