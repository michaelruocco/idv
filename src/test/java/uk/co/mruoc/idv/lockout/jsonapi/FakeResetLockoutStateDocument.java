package uk.co.mruoc.idv.lockout.jsonapi;

public class FakeResetLockoutStateDocument extends ResetLockoutStateDocument {

    FakeResetLockoutStateDocument() {
        super(new FakeResetLockoutStateAttributes());
    }

}
